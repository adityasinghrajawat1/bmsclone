package com.driver.bookMyShow.Exceptions;

import com.driver.bookMyShow.constant.Messages;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("not present");
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public static NotFoundException getExceptionWithDesc(String entity) {
        return new NotFoundException(entity + Messages.ONE_TAB + Messages.NOT_FOUND + Messages.DOT);
    }
}

