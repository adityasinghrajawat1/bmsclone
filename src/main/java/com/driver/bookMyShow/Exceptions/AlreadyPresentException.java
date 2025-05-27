package com.driver.bookMyShow.Exceptions;

import com.driver.bookMyShow.constant.Messages;
import org.springframework.mail.MailException;

public class AlreadyPresentException extends RuntimeException {
    public AlreadyPresentException() {
        super("already present");
    }

    public AlreadyPresentException(String msg) {
        super(msg);
    }

    public static AlreadyPresentException getExceptionWithDesc(String entity) {
        return new AlreadyPresentException(entity + Messages.ONE_TAB + Messages.ALREADY_PRESENT + Messages.DOT);
    }
}
