package com.pvlrs.pagerdutysnoozer.controller;

import com.pvlrs.pagerdutysnoozer.controller.request.SnoozeRequest;
import com.pvlrs.pagerdutysnoozer.dto.UserDto.UserDetailDto;
import com.pvlrs.pagerdutysnoozer.service.SnoozingService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*  snooze /snuːz/
    verb: snooze; 3rd person present: snoozes; past tense: snoozed;
    past participle: snoozed; gerund or present participle: snoozing
        have a short, light sleep, especially during the day.
    noun: snooze; plural noun: snoozes
        a short, light sleep, especially during the day. */
@RestController
@RequestMapping("/v1/snoozes")
public record SnoozeController(SnoozingService snoozingService) {

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void scheduleSnoozes(@Validated @RequestBody SnoozeRequest snoozeRequest) {
        snoozingService.scheduleSnoozes(snoozeRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDetailDto> getScheduledSnoozes() {
        return snoozingService.getScheduledSnoozes();
    }
}
