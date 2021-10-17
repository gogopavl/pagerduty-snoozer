package com.pvlrs.pagerdutysnoozer.controller;

import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto;
import com.pvlrs.pagerdutysnoozer.service.IncidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/incidents")
public record IncidentController(IncidentService incidentService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public IncidentsDto getIncidents() {
        log.debug("controller");
        return incidentService.getIncidents(List.of("PBT9C2B"), List.of("triggered"));
    }
}
