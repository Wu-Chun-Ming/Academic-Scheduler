package io.github.wcm.schedulemanager.domain.enums;

public enum Scope {
    OFFICIAL("Official"),
    PERSONAL("Personal");

    private final String label;

    Scope(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
