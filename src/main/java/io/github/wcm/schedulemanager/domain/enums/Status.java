package io.github.wcm.schedulemanager.domain.enums;

public enum Status {
    PENDING("Pending"),
    EXPIRED("Expired"),
    SUBMITTED("Submitted");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
