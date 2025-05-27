package com.driver.bookMyShow.Exceptions;

public class RequestFailedException extends RuntimeException {
    public RequestFailedException() {
        super("Request failed! invalid request body");
    }

    public RequestFailedException(String msg) {
        super(msg);
    }
}
