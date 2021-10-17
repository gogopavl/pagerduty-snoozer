package com.pvlrs.pagerdutysnoozer.service;

import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto;

import java.util.List;

public interface IncidentService {

    IncidentsDto getIncidents(List<String> userIds, List<String> statuses);
}
