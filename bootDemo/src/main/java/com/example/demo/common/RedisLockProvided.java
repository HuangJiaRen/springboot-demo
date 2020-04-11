package com.example.demo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-12-12 22:48
 */
@Component
@ConditionalOnProperty(name = "frame.lock.provider", havingValue = "redis")
public class RedisLockProvided implements ResourceLock {
private Logger logger = LoggerFactory.getLogger(RedisLockProvided.class);

public static final String FIX_REDIS_KEY_PREFIX = "/RES_LOCK";

/**
 * 纪元时间：redis主从切换时，获取锁的操作需要在切换完成后等待一个超时周期，避免出现主从切换导致的锁状态丢失
 */
private volatile Long epochTime = 0L;

@Qualifier("LockRedis")
@Autowired
private RedisConnectionFactory redisConnectionFactory;

@Autowired
private RedisConf redisConf;


        Set<MasterListener> masterListenerSet = new HashSet<>();
/**
 * 阻塞试获取锁信息
 *
 * @param key        锁对象
 * @param expireTime 过期时间
 * @param timeOut    超时时间
 */
@Override
public Lock lock(String key, Long expireTime, Long timeOut) throws InterruptedException, TimeoutException {
        long starTime = System.currentTimeMillis();
        Lock lock = getInvaitLock(key,expireTime);
        while (lock == null) {
        if (System.currentTimeMillis() - starTime >= timeOut) {
        throw new TimeoutException("lock get time out ");
        }
        Thread.sleep(600);
        lock = getInvaitLock(key,expireTime);
        return lock;
        }
        return null;
        }

/**
 * 获取锁信息
 * @param key
 * @param expireTime
 * @return
 */
private Lock getInvaitLock(String key,long expireTime) {
        if (expireTime <= 0) {
        throw new IllegalArgumentException("lock expire less than 0  ");
        }

        if (System.currentTimeMillis() - epochTime < expireTime) {
        try{
        //当 当前系统的时间和纪元时间之差小于过期时间
        Thread.sleep(expireTime - (System.currentTimeMillis() - epochTime ));
        }catch (InterruptedException in) {
        //异常线程中断
        Thread.interrupted();
        logger.error(" LOCK THREAF ERROR  ",in);
        }
        }
        String warpKey = wrapKey(key);

        //密钥
        String lockVal = UUID.randomUUID().toString();
        lockVal = Thread.currentThread().getName() + ":" + lockVal;

        //设置锁的超时时间
        RedisConnection connection = redisConnectionFactory.getConnection();
        if (connection == null) {
        throw new RuntimeException("redis connection not get ");
        }

        boolean lockExpire = connection.set(warpKey.getBytes(),lockVal.getBytes()
        , Expiration.from(expireTime, TimeUnit.MILLISECONDS),
        RedisStringCommands.SetOption.ifAbsent());

        if (lockExpire) {
        Lock lock = new Lock(this);
        lock.setKey(warpKey);
        lock.setInternalLock(lockVal);
        return lock;
        } else {
        //lock fail
        return null;
        }

        }


/**
 * 获取 key
 * @param simpleKey
 * @return
 */
protected String wrapKey(String simpleKey) {
        StringBuffer sb = new StringBuffer(FIX_REDIS_KEY_PREFIX)
        .append(redisConf.getKeyPrefix())
        .append(simpleKey);
        return sb.toString();
        }
/**
 * 获取锁信息
 *
 * @param key        锁对象
 * @param expireTime 过期时间
 * @return
 */
@Override
public Lock tryLock(String key, Long expireTime) {
        return getInvaitLock(key,expireTime);
        }

/**
 * 释放锁信息
 *
 * @param lock
 */
@Override
public void unLock(Lock lock) {
        /**
         * 解锁过程如下:
         * 1.watch要解锁的key
         * 2.get该key中的lockVal,并判断lockVal是否为空
         *  为空说明该锁已超时释放,无需进一步处理,解锁结束;
         *  非空则继续执行后续步骤.
         * 3.比较redis中get到的lockVal是否与本地缓存的lockVal相同
         *  如不同,说明要释放的锁已经被redis超时释放,并且被其他线程重新获取,此时不可再执行删除操作,解锁结束;
         *  如相同则继续执行后续步骤.
         * 4.在事务中删除该key,此步骤可能会有三种结果
         *  其一,事务操作被打断,说明watch期间,该锁被redis超时释放,然后又被其他线程获取,概率极低
         *  其二,事务执行成功,删除操作的返回值为0,说明watch期间,该锁被redis超时释放
         *  其三,事务执行成功,删除操作的返回值为1,表明正常删除了锁
         *
         *  redis中的expire并不会被watch觉察,但是watch期间如果在key超时expire,之后又被其他线程重新set,watch将会觉察到并打断事务
         */

        RedisConnection connection = redisConnectionFactory.getConnection();
        try{
        //redis 事务
        //参考博客 https://www.cnblogs.com/liuchuanfeng/p/7190654.html
        connection.watch(lock.getKey().getBytes());
        byte[] bytes = connection.get(lock.getKey().getBytes());
        String lockVal;
        if(bytes != null){
        lockVal = new String(bytes);
        if(lock.getInternalLock().equals(lockVal)){
        connection.multi();
        //del 返回值为被删除的key的数量
        connection.del(lock.getKey().getBytes());
        List<Object> results = connection.exec();
        logger.debug(Thread.currentThread().getName() + ":" + results);
        if(results.size()==0){
        //multi被watch打断,可能是watch期间,碰巧key expire, 然后其他线程抢先获取了锁, 发生的概率极低
        logger.info("[framework] [lock] failed to do unlock, lock expired, and acquired by other thread, lockVal:" + lockVal);
        } else if(results.size()==1 && (Long)results.get(0) == 0) {
        //result==0时,表示watch期间,碰巧key expire, 发生概率较低
        logger.info("[framework] [lock] failed to do unlock, lockVal:" + lockVal);
        } else if(results.size()==1 && (Long)results.get(0) == 1) {
        //result==1时,表示正常解锁
        }
        }else {
        //要解除的锁已经被其他线程占有
        logger.debug("[framework] [lock] failed to do unlock, not the lock's owner, lockVal:" + lockVal);
        }
        } else {
        //锁不存在,expire了
        logger.debug("[framework] [lock] failed to do unlock, lock not exist, it may be expired, lockVal:" + lock.getInternalLock());
        }
        }finally {
        if(connection != null) {
        connection.unwatch();
        connection.close();
        }
        }

        }

/**
 * 数据预加载
 */
@PostConstruct
public  void init() {
        /**
         * 初始化系统时设置为当前的时间，使得系统刚初始化时，业务系统要等待一个锁的周期
         * 防止业务系统重启的时候正好是主备切换，防止锁的状态丢失
         */
        this.epochTime = System.currentTimeMillis();

        /**
         * 为每个哨兵开启都开启一个线程不断的去订阅 +switch-master频道，发生主从切换的时候 ，更新纪元的时间，
         */
        for (String sentinel: redisConf.getSentinel().getNodes()) {
final HostAndPort hap = HostAndPort.parseString(sentinel);
        MasterListener masterListener = new MasterListener(sentinel,hap.getPort(),hap.getHost());
        //设置为守护线程，主程序结束。jvm退出操作。---当主程序没有线程运行时，哨兵订阅的线程就没有什么意义
        masterListener.setDaemon(true);
        masterListenerSet.add(masterListener);
        masterListener.start();
        }
        }

/**
 * redis 监听
 */
protected class MasterListener extends  Thread{

    /**
     * 节点名称
     */
    private String masterName;

    /**
     * 端口号
     */
    private int port;

    /**
     * host
     */
    private String host;

    /**
     * 重试等待时间
     */
    private  long subscribeRetryWaitTimeMillis = 5000;

    /**
     * JEDIS CLIEN
     */
    private volatile Jedis jedis;

    /**
     * 线程执行标识，防止执行时  被其他线程篡改
     * 因为 volatile 修改的变量在操作的时候不是原子性操作，修改修饰的变量的时候，可能会存在线程安全的问题现象
     * 在这里使用 Atomic的工具类
     */
    private AtomicBoolean runing = new AtomicBoolean(false);

    /**
     * 无参构造
     */
    MasterListener() {}

    /**
     * 有参构造
     * @param masterName
     * @param port
     * @param host
     */
    MasterListener(String masterName, int port, String host) {
        super(String.format("MasterListener-%s-[%s:%d]", masterName, host, port));
        this.masterName = masterName;
        this.host = host;
        this.port = port;
    }

    /**
     * 有参构造
     * @param masterName
     * @param port
     * @param host
     * @param subscribeRetryWaitTimeMillis
     */
    MasterListener(String masterName, int port, String host,Long subscribeRetryWaitTimeMillis) {

        this(masterName,port,host);
        this.subscribeRetryWaitTimeMillis = subscribeRetryWaitTimeMillis;
    }

    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        runing.set(true);
        while (runing.get()) {
            jedis = new Jedis(host,port);
            try{
                //检查  redis 是否已经被关闭
                if (!runing.get()) {
                    break;
                }
                //不断的订阅redis信息
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        logger.info("[framework] [lock] Sentinel " + host + ":" + port + " published: " + message + ".");
                        String[] masterMessage = message.split("：");
                        if (masterMessage.length > 3) {
                            if (masterName.equals(masterMessage[0])) {
                                epochTime = System.currentTimeMillis();
                            } else {
                                logger.info("[framework] [lock] Ignoring message on +switch-master for master name "
                                        + masterMessage[0] + ", our master name is " + masterName);
                            }
                        } else {
                            logger.info("[framework] [lock] Invalid message received on Sentinel " + host + ":" + port
                                    + " on channel +switch-master: " + message);
                        }
                    }
                },"+switch-master");
            }catch (JedisConnectionException e) {
                //sleep subscribeRetryWaitTimeMillis 等待重新连接redis
                if (runing.get()) {
                    logger.error(" thrad sleep subscribeRetryWaitTimeMillis wait retry connection ");

                    try {
                        Thread.sleep(subscribeRetryWaitTimeMillis);
                    }catch (InterruptedException in) {
                        logger.error("[framework] [lock] Sleep interrupted: ", in);
                    }
                } else {
                    logger.info("[framework] [lock] Unsubscribing from Sentinel at " + host + ":" + port);
                }

            } finally {
                jedis.close();
            }


        }
    }

    /**
     * 关闭
     */
    public void shutDown() {
        runing.set(false);
        try{
            logger.info(" redis lock is shutdown ");
            jedis.disconnect();
        }catch (Exception e) {
            logger.info(" redis lock is shutdown is error ",e);
        }
    }
}


    @PreDestroy
    public void destroy(){
        for (MasterListener m : masterListenerSet) {
            m.shutDown();
        }
    }
}