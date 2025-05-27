package com.driver.bookMyShow.Enums;

import com.driver.bookMyShow.Exceptions.InvalidException;

import java.util.Arrays;

public enum SeatType {
    GOLD,
    PLATINUM,
    RECLINER,
    COUPLERECLINER;

    public static void validate(String type) {
        boolean isValid = Arrays.stream(SeatType.values())
                .anyMatch(seat -> seat.name().equalsIgnoreCase(type));

        if (!isValid)
            throw new InvalidException("Invalid seat type: " + type);
    }

    public static SeatType getEnum(String value) {
        validate(value);
        return SeatType.valueOf(SeatType.class, value.toLowerCase());
    }
}
