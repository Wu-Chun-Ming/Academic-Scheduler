package io.github.wcm.schedulemanager.domain.enums;

public enum ProgrammeType {
    FOUNDATION("foundation"), 
    UNDERGRADUATE("undergraduate");

    private final String value;

    ProgrammeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
