package com.driver.bookMyShow.utils;

import java.util.UUID;

public class Utils {
    public static String generateUUID(int length) {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, length);
    }
}
