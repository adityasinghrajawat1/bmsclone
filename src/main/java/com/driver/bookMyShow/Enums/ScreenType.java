package com.driver.bookMyShow.Enums;

import com.driver.bookMyShow.Exceptions.RequestFailedException;
import com.driver.bookMyShow.constant.Messages;

import java.util.Arrays;

public enum ScreenType {
    TWO_D("2D"),
    IMAX("IMAX"),
    THREE_D("3D"),
    SCREEN_X("SCREEN X"),
    ICE("ICE"),
    FOUR_D_X("4DX"),
    IMAX_TWO_D("IMAX 2D");

    private final String screenName;

    ScreenType(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenName() {
        return screenName;
    }

    public static void validate(String name) {
        boolean isValid = Arrays.stream(ScreenType.values())
                .anyMatch(s -> s.getScreenName().equalsIgnoreCase(name));

        if (!isValid)
            throw new IllegalArgumentException("Invalid Screen Type : " + name);
    }

    public static ScreenType fromString(String name) {
        return Arrays.stream(ScreenType.values())
                .filter(s -> s.getScreenName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new RequestFailedException(Messages.INVALID + Messages.ONE_TAB + Messages.SCREEN_TYPE + Messages.DOT));
    }
}
