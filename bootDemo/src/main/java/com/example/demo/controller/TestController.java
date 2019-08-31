package com.example.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.BlogProperties;
import com.example.demo.bean.MongoUser;
import com.example.demo.bean.User;
import com.example.demo.common.ServiceResult;
import com.example.demo.config.EsConfig;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mgservice.UserService;
import com.example.demo.pojo.ElasticsearchQuery;
import com.example.demo.service.RedisService;
import com.example.demo.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.InboundMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
public class TestController {
    @Autowired
    private BlogProperties blogProperties;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserService userService;

    @ApiOperation(value="swagger第一个接口", notes="hello接口")
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(){
        System.out.println(blogProperties.getTile());
        return "hello springboot!";
    }

    @ApiOperation(value="整合mybatis", notes="整合mybatis")
    @RequestMapping(value = "/db",method = RequestMethod.GET)
    public List<Map<String,Object>> db(){
        return userMapper.listUsers();
    }


    @ApiOperation(value = "整合redis", notes = "整合redis")
    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public String reids() {
        redisService.setString("my1","20171213");
        return redisService.getString("my1");
    }

    @ApiOperation(value = "整合redisList", notes = "整合redisList")
    @RequestMapping(value = "/getRedisList", method = RequestMethod.GET)
    public ServiceResult redisList() {
        ServiceResult serviceResult = new ServiceResult();
        List<User> listTest = redisService.getList("listTest");
        serviceResult.setData(listTest);
        return serviceResult;
    }

    @ApiOperation(value = "整合redis的过期时间", notes = "整合redis的过期时间")
    @RequestMapping(value = "/addRedisList", method = RequestMethod.POST)
    public ServiceResult addsList() {
        ServiceResult serviceResult = new ServiceResult();
        List userList = Arrays.asList(new User("张三",18,1)
                ,new User("李四",20,1));
        redisService.setList("listTest", userList);

        Jedis jedis = redisUtil.getJedis();
        jedis.expire("listTest", 10);
        return serviceResult;
    }

    @ApiOperation(value = "整合redis的事务", notes = "整合redis的事务")
    @RequestMapping(value = "/testRedisTran", method = RequestMethod.POST)
    public ServiceResult testRedisTran() {
        ServiceResult serviceResult = new ServiceResult();

        //开启事务
        Jedis jedis = redisUtil.getJedis();
        Transaction transtacTion = jedis.multi();
        //执行事务内的redis命令
        transtacTion.set("tran1", "hello");
        transtacTion.set("tran2", "world");
        //提交事务
        List<Object> exec = transtacTion.exec();
        //事务中每条命令的执行结果
        serviceResult.setData(exec);
        return serviceResult;
    }

    @ApiOperation(value = "整合redis的事务watch", notes = "整合redis的事务")
    @RequestMapping(value = "/testRedisWatch", method = RequestMethod.POST)
    public ServiceResult testRedisTran2() {
        ServiceResult serviceResult = new ServiceResult();

        JedisPool jedisPool = new JedisPool("49.234.238.59");
        Jedis jedis = jedisPool.getResource();
        // 设定 nowatch 的初始值为 hello
        jedis.set("watchtest","hello");
        // 使用 watch 命令watch "watchtest"
        jedis.watch("watchtest");
        //开启事务
        Transaction multi = jedis.multi();
        //另外一个jedis客户端对watchtest进行append操作
        jedisPool.getResource().append("watchtest", " xxx");
        // 事务内部对watchtest进行append操作
        multi.append("watchtest", " world");
        //提交事务
        multi.exec();

        serviceResult.setData(jedis.get("watchtest"));
        return serviceResult;
    }

    @ApiOperation(value = "整合es-7.1.1", notes = "整合es-7.1.1")
    @GetMapping("/order/getById/{id}")
    public Map<String,Object> getOrder(@PathVariable("id")String id){
        GetRequest getRequest=new GetRequest("logs-client-2019.08.12","_doc",id);
        Map map=new HashMap();
        GetResponse response=null;
        try{
            response= restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.isExists()){
            map.put("success",true);
            map.put("data",response.getSource());
        }else{
            map.put("success",false);
        }
        return map;


    }

    /**
     * 增加mongo用户
     */
    @ApiOperation(value = "整合mongodb", notes = "整合mongodb")
    @PostMapping("addMgUser")
    public void addMgUser() {
        MongoUser mongoUser = new MongoUser("张思", 22, 1);
        userService.saveUser(mongoUser);
    }

    /**
     * 取出mongo所有所有用户
     */
    @ApiOperation(value = "整合mongodb", notes = "整合mongodb")
    @GetMapping("getMongoUsers")
    public List<MongoUser> getMongoUser() {
        List<MongoUser> user = userService.getUsers();
        return user;
    }

    @ApiOperation(value = "整合es-7.1.1", notes = "整合es-7.1.1")
    @GetMapping("/order/termQuery")
    public ServiceResult termQuery(ElasticsearchQuery req) throws IOException {
        ServiceResult serviceResult = new ServiceResult();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10000);
        //排序
        searchSourceBuilder.sort(SortBuilders.fieldSort("@timestamp").order(SortOrder.DESC));
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("id",req.getId());
        if (StringUtils.isEmpty(req.getKeyWord())){
            MatchPhraseQueryBuilder moreLikeThisQuery = QueryBuilders.matchPhraseQuery("message",req.getKeyWord());
            boolQueryBuilder.must(moreLikeThisQuery);
        }
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("timestamp");
//        rangeQueryBuilder.gte(req.getFirstTime());
//        rangeQueryBuilder.lte(req.getLastTime());
//        boolQueryBuilder.must(queryBuilder);
//        boolQueryBuilder.must(rangeQueryBuilder);
        searchSourceBuilder.query(boolQueryBuilder);
        SearchRequest searchRequest = new SearchRequest(req.getChatRoomIndex());
        searchRequest.routing(req.getChatRoomType());
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        List<Object> list = new ArrayList<>();
        if (searchHits.length > 0){
            for (SearchHit hit : searchHits) {
                list.add(hit.getSourceAsMap()) ;
            }
        }

        serviceResult.setData(list);

        return serviceResult;

    }

    @ApiOperation(value = "整合es-7.1.1的update方法", notes = "整合es-7.1.1的update方法")
    @PostMapping("/order/update/")
    public String update(ElasticsearchQuery req) throws IOException {
        UpdateRequest request=new UpdateRequest(req.getChatRoomIndex(),req.getId());
        Map<String,Object> temp=new HashMap<>();
        if(!ObjectUtils.isEmpty(req.getKeyWord())){
            temp.put("message",req.getKeyWord());
        }

        if(!ObjectUtils.isEmpty(req.getPath())){
            temp.put("path",req.getPath());
        }

        request.doc(temp);

        return restHighLevelClient.update(request,RequestOptions.DEFAULT).status().name();

    }

    @DeleteMapping("/order/delete/{id}")
    public String deleteOrder(@PathVariable("id") String id) {
        DeleteRequest request = new DeleteRequest("logs-client-2019.08.12", id);
        try {
            DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            return response.status().name();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
