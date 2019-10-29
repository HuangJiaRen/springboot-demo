package com.example.demo.common;

/**
 * @author huangli
 * @package:com.ganinfo.common.enums
 * @className:DataSourceType
 * @description:枚举区分读写库
 **/
public enum DataSourceType {
    read("read", "从库"), write("write", "主库");

    DataSourceType(String type, String name) {
        this.type = type;
        this.name = name;
    }


    private String type;
    private String name;


    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }


}
