package io.github.wcm.schedulemanager.domain;

public enum Scope {
    PERSONAL("personal"), 
    OFFICIAL("official");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
