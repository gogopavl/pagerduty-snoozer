package com.pvlrs.pagerdutysnoozer.client;

import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto;
import com.pvlrs.pagerdutysnoozer.dto.OnCallsDto;
import com.pvlrs.pagerdutysnoozer.dto.SnoozeDurationDto;
import com.pvlrs.pagerdutysnoozer.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@FeignClient(value = "pagerDutyClient", url = "${com.pagerduty.host}", decode404 = true)
public interface PagerDutyClient {

    @RequestMapping(method = RequestMethod.GET, value = "/incidents")
    IncidentsDto getIncidents(@RequestParam(name = "user_ids[]") List<String> userIds,
                              @RequestParam(name = "statuses[]") List<String> statuses);

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    Optional<UserDto> getUserDetails(@PathVariable(name = "userId") String userId);

    @RequestMapping(method = RequestMethod.GET, value = "/oncalls")
    OnCallsDto getUserOnCalls(@RequestParam(name = "user_ids[]") String userId,
                              @RequestParam(name = "since") LocalDateTime since,
                              @RequestParam(name = "until") LocalDateTime until,
                              @RequestParam(name = "earliest") boolean earliest);

    @RequestMapping(method = RequestMethod.PUT, value = "/incidents")
    IncidentsDto acknowledgeIncidents(@RequestBody IncidentsDto incidentsDto);

    @RequestMapping(method = RequestMethod.POST, value = "/incidents/{incidentId}/snooze")
    void snoozeIncident(@PathVariable String incidentId,
                                @RequestBody SnoozeDurationDto snoozeDurationDto);

}
