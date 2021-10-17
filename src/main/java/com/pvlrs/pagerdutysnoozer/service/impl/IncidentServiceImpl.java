package com.pvlrs.pagerdutysnoozer.service.impl;

import com.pvlrs.pagerdutysnoozer.client.PagerDutyClient;
import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto;
import com.pvlrs.pagerdutysnoozer.service.IncidentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record IncidentServiceImpl(PagerDutyClient pagerDutyClient) implements IncidentService {

    @Override
    public IncidentsDto getIncidents(List<String> userIds, List<String> statuses) {
        return pagerDutyClient.getIncidents(userIds, statuses);
    }
}
