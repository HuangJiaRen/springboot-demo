package com.example.demo.common;

import java.util.List;
import java.util.Map;

public class ESReturn<T> {
    private List<Map<String,Object>> esList;

    private Long total;

    private T data;

    public ESReturn() {
    }

    public List<Map<String, Object>> getEsList() {
        return esList;
    }

    public void setEsList(List<Map<String, Object>> esList) {
        this.esList = esList;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
