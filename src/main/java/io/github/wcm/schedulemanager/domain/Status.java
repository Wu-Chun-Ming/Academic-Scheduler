package io.github.wcm.schedulemanager.domain;

public enum Status {
    PENDING("pending"),
    EXPIRED("expired"),
    SUBMITTED("submitted");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
