package com.driver.bookMyShow.Enums;


import com.driver.bookMyShow.Exceptions.RequestFailedException;
import com.driver.bookMyShow.constant.Messages;

import java.util.Arrays;

public enum ShowStatus {
    RUNNING("Running"),
    COMPLETED("Completed"),
    INITIATED("Initiated");

    private String displayName;

    ShowStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(ShowStatus.values())
                .anyMatch(s -> s.getDisplayName().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid showStatus: " + name);
    }

    public static ShowStatus fromString(String name) {
        return Arrays.stream(ShowStatus.values())
                .filter(s -> s.getDisplayName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RequestFailedException(Messages.INVALID + Messages.ONE_TAB + Messages.SHOW + Messages.ONE_TAB + Messages.STATUS + Messages.DOT));
    }
}
