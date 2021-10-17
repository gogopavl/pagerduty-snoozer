package com.pvlrs.pagerdutysnoozer.scheduling;

import com.pvlrs.pagerdutysnoozer.service.SnoozingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.pvlrs.pagerdutysnoozer.constants.TemporalConstants.ONE_MINUTE_IN_MILLIS;

@Slf4j
@Component
public record PagerDutyIncidentAckScheduler(SnoozingService snoozingService) {

    @Scheduled(fixedDelay = ONE_MINUTE_IN_MILLIS)
    public void scheduleIncidentSnoozing() {
        log.debug("Snoozer invoked, snoozing incidents for enabled users");
        snoozingService.snoozeTriggeredIncidents();
    }
}
