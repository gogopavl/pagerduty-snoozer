package com.pvlrs.pagerdutysnoozer.enumerated;

import lombok.Getter;

public enum PagerDutyIncidentStatus {

    TRIGGERED("triggered"),
    ACKNOWLEDGED("acknowledged");

    @Getter
    private final String name;

    PagerDutyIncidentStatus(String name) {
        this.name = name;
    }
}
