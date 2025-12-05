package io.github.wcm.schedulemanager.domain.enums;

public enum ProgrammeType {
    FOUNDATION("Foundation"), 
    UNDERGRADUATE("Undergraduate");

    private final String label;

    ProgrammeType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
