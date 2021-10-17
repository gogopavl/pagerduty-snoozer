package com.pvlrs.pagerdutysnoozer.service;

import com.pvlrs.pagerdutysnoozer.controller.request.SnoozeRequest;
import com.pvlrs.pagerdutysnoozer.dto.UserDto;

import java.util.List;

public interface SnoozingService {

    void scheduleSnoozes(SnoozeRequest snoozeRequest);

    List<UserDto.UserDetailDto> getScheduledSnoozes();

    void snoozeTriggeredIncidents();
}
