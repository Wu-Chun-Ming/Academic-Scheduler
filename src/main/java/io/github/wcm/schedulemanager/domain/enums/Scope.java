package io.github.wcm.schedulemanager.domain.enums;

public enum Scope {
    OFFICIAL("official"),
    PERSONAL("personal");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
