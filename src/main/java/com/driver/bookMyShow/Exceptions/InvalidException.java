package com.driver.bookMyShow.Exceptions;

import com.driver.bookMyShow.constant.Messages;

public class InvalidException extends RuntimeException {
    public InvalidException() {
        super("Invalid.");
    }

    public InvalidException(String msg) {
        super(msg);
    }

    public static InvalidException getExceptionWithDesc() {
        return new InvalidException(Messages.INVALID + Messages.ONE_TAB + Messages.SEAT_TYPE + Messages.DOT);
    }
}
