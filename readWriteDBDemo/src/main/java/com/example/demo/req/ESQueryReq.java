package com.example.demo.req;

import lombok.Data;

import java.util.Date;

@Data
public class ESQueryReq {
    /**
     * ES 设置的type
     */
    private String chatRoomType;

    /**
     * ES 设置的index
     */
    private String chatRoomIndex;

    /**
     * 要查询的关键字
     */
    private String keyWord;


    private Date firstTime;

    private Date lastTime;

    private int chatroomCode;

}
