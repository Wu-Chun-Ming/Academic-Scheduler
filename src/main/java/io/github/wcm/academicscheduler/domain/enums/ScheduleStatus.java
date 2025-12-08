package io.github.wcm.academicscheduler.domain.enums;

public enum ScheduleStatus {
    PENDING("Pending"),
    EXPIRED("Expired"),
    SUBMITTED("Submitted");

    private final String label;

    ScheduleStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
