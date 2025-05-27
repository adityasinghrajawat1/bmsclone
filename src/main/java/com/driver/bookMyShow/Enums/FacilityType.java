package com.driver.bookMyShow.Enums;

import java.util.Arrays;

public enum FacilityType {
    TICKETCANCELLATION("Ticket Cancellation"),
    FANDB("F and B"),
    MOBILETICKET("Mobile Ticket"),
    GAMINGZONE("Gaming Zone"),
    WHEELCHAIR("Wheel Chair"),
    PARKING("Parking");

    private final String displayName;

    FacilityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static void validate(String type) {
        boolean isValid = Arrays.stream(FacilityType.values())
                .anyMatch(f -> f.getDisplayName().equalsIgnoreCase(type));

        if (!isValid) {
            throw new IllegalArgumentException("Invalid Facility Type: " + type);
        }
    }
}
