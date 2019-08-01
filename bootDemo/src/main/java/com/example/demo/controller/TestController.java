package com.example.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.BlogProperties;
import com.example.demo.bean.MongoUser;
import com.example.demo.bean.User;
import com.example.demo.common.ServiceResult;
import com.example.demo.mapper.UserMapper;
import com.example.demo.mgservice.UserService;
import com.example.demo.service.RedisService;
import com.example.demo.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.elasticsearch.transport.InboundMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

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

    @ApiOperation(value = "整合addList", notes = "整合redisList")
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

    @GetMapping("/order/getById/{id}")
    public Map<String,Object> getOrder(@PathVariable("id")String id){
        GetRequest getRequest=new GetRequest("logs-beat45-2019.06.28","_doc",id);
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

//    @GetMapping("/order/termQuery/{id}")
//    public Map<String,Object> termQuery(@PathVariable("id")String id){
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.from(0);
//        searchSourceBuilder.size(10000);
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("CHATROOM_CODE",req.getChatroomCode());
//        if (StringUtils.isEmpty(req.getKeyWord())){
//            moreLikeThisQuery = QueryBuilders.matchPhraseQuery("TXT",req.getKeyWord());
//            boolQueryBuilder.must(moreLikeThisQuery);
//        }
//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("FIRST_UPDATETIME");
//        rangeQueryBuilder.gte(req.getFirstTime());
//        rangeQueryBuilder.lte(req.getLastTime());
//        boolQueryBuilder.must(queryBuilder);
//        boolQueryBuilder.must(rangeQueryBuilder);
//        searchSourceBuilder.query(boolQueryBuilder);
//        SearchRequest searchRequest = new SearchRequest(chatRoomIndex);
//        searchRequest.routing(chatRoomType);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse response = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
//        SearchHits hits = response.getHits();
//        SearchHit[] searchHits = hits.getHits();
//        List<Object> list = new ArrayList<>();
//        if (searchHits.length > 0){
//            for (SearchHit hit : searchHits) {
//                list.add(hit.getSourceAsMap()) ;
//            }
//        }
//        return  rr.returnSuccessResult(list,requestReport);
//
//    }


}
