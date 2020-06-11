package com.example.assignment6.beans;

public enum Priority {
    LOW(0), MEDIUM(1), HIGH(2);

    public final int intValue;

    Priority(int intValue) {
        this.intValue = intValue;
    }

    public static Priority from(int intValue) {
        for (Priority priority : Priority.values()) {
            if (priority.intValue == intValue) {
                return priority;
            }
        }
        return LOW;
    }
}
