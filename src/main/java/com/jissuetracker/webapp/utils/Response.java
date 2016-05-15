package com.jissuetracker.webapp.utils;

/**
 * Created by jovin on 5/12/15.
 */

//Custom class for creating output response of ajax requests
public class Response<T> {

    private T data;

    public Response() {}

    public Response(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
