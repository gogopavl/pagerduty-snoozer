package com.pvlrs.pagerdutysnoozer.enumerated;

import lombok.Getter;

public enum PagerDutyResourceType {

    INCIDENT("incident");

    @Getter
    private final String name;

    PagerDutyResourceType(String name) {
        this.name = name;
    }
}
