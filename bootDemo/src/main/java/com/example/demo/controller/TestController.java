package com.example.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.demo.BlogProperties;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.RedisService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private BlogProperties blogProperties;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisService redisService;


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @ApiOperation(value="第一个接口", notes="hello接口")
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
