package com.pvlrs.pagerdutysnoozer.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class SnoozeRequest {

    private List<String> userIds;
    private Boolean enableSnoozing;
}
