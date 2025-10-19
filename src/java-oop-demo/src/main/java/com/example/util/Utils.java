package com.example.util;

public class Utils {
    // Utility methods can be added here
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120;
    }
}