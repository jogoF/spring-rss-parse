package com.jogo.jnewsrss.advice;

public class AppResp {
    private int statusCode;
    private Object message;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public AppResp() {
    }

    public AppResp(int statusCode, Object message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}