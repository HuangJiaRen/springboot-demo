package com.example.demo.common;

public final class ServiceResult<T> {
    private String message;
    private int code;
    private T data;

    public ServiceResult() {
        this.createServiceResult(HttpResultEnum.SUCCESS);
    }

    public ServiceResult(HttpResultEnum r) {
        this.createServiceResult(r);
    }

    public ServiceResult(int code, String message) {
        this.createServiceResult(code, message);
    }

    private void createServiceResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private void createServiceResult(HttpResultEnum r) {
        this.code = r.getStatus();
        this.message = r.getMessage();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
