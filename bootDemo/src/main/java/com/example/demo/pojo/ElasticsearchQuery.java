package com.example.demo.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author huangli
 * @version 1.0
 * @description TODO
 * @date 2019-08-22 16:02
 */
@Data
public class ElasticsearchQuery {
    private String chatroomCode;
    private String keyWord;
    private Date firstTime;
    private Date lastTime;
    private String chatRoomIndex;
    private String chatRoomType;
    private String id;
    private String path;
}
