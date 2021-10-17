package com.pvlrs.pagerdutysnoozer.service.impl;

import com.pvlrs.pagerdutysnoozer.client.PagerDutyClient;
import com.pvlrs.pagerdutysnoozer.controller.request.SnoozeRequest;
import com.pvlrs.pagerdutysnoozer.domain.redis.User;
import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto;
import com.pvlrs.pagerdutysnoozer.dto.IncidentsDto.IncidentDto;
import com.pvlrs.pagerdutysnoozer.dto.OnCallsDto;
import com.pvlrs.pagerdutysnoozer.dto.OnCallsDto.OnCallDetailsDto;
import com.pvlrs.pagerdutysnoozer.dto.SnoozeDurationDto;
import com.pvlrs.pagerdutysnoozer.dto.UserDto.UserDetailDto;
import com.pvlrs.pagerdutysnoozer.enumerated.PagerDutyIncidentStatus;
import com.pvlrs.pagerdutysnoozer.enumerated.PagerDutyResourceType;
import com.pvlrs.pagerdutysnoozer.service.SnoozingService;
import com.pvlrs.pagerdutysnoozer.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.pvlrs.pagerdutysnoozer.constants.TemporalConstants.DEFAULT_SNOOZE_OFFSET;
import static com.pvlrs.pagerdutysnoozer.constants.TemporalConstants.SNOOZE_MARGIN;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Slf4j
@Service
public record SnoozingServiceImpl(UserService userService,
                                  PagerDutyClient pagerDutyClient) implements SnoozingService {

    @Override
    public void scheduleSnoozes(SnoozeRequest snoozeRequest) {
        List<User> users = retrievePagerDutyUsers(snoozeRequest);
        if (snoozeRequest.getEnableSnoozing()) {
            userService.saveUsers(users);
        } else {
            userService.removeUsers(users);
        }
    }

    private List<User> retrievePagerDutyUsers(SnoozeRequest snoozeRequest) {
        List<User> pagerDutyUsers = new ArrayList<>();
        snoozeRequest.getUserIds().stream()
                .filter(StringUtils::isNotBlank)
                .forEach(userId -> {
                    pagerDutyClient.getUserDetails(userId.trim()).ifPresent(presentUser -> {
                        UserDetailDto userDetailDto = presentUser.getUser();
                        pagerDutyUsers.add(User.builder().id(userDetailDto.getId())
                                .name(userDetailDto.getName()).build());
                    });
                });
        return pagerDutyUsers;
    }

    @Override
    public List<UserDetailDto> getScheduledSnoozes() {
        List<UserDetailDto> snoozingEnabledUsers = new ArrayList<>();
        userService.getAllUsers().forEach(user -> {
            snoozingEnabledUsers.add(UserDetailDto.fromUser(user));
        });
        return snoozingEnabledUsers;
    }

    @Override
    public void snoozeTriggeredIncidents() {
        userService.getAllUsers().forEach(user -> {
            OnCallsDto onCalls = fetchUserShift(user);

            log.debug(MessageFormat.format("Retrieving incidents for user {0}", user.getId()));
            IncidentsDto triggeredIncidents = pagerDutyClient.getIncidents(List.of(user.getId()),
                    List.of(PagerDutyIncidentStatus.TRIGGERED.getName()));
            boolean userHasTriggeredIncidents = isNotEmpty(triggeredIncidents.getIncidents());

            if (userHasTriggeredIncidents) {
                IncidentsDto acknowledgedIncidents = acknowledgeTriggeredIncidents(triggeredIncidents);
                snoozeAcknowledgedIncidents(onCalls, acknowledgedIncidents);
            }
        });
    }

    private OnCallsDto fetchUserShift(User user) {
        LocalDateTime startOfToday = LocalDateTime.now().toLocalDate().atTime(LocalTime.MIN);
        LocalDateTime endOfToday = LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX);
        log.debug(MessageFormat.format("Retrieving on-call shift for user {0}, start = {1} and end = {2}",
                user.getId(), startOfToday, endOfToday));
        return pagerDutyClient.getUserOnCalls(user.getId(), startOfToday, endOfToday, true);
    }

    private IncidentsDto acknowledgeTriggeredIncidents(IncidentsDto triggeredIncidents) {
        List<IncidentDto> incidentsToBeAcknowledged = triggeredIncidents.getIncidents().stream()
                .map(incident -> IncidentDto.builder()
                        .id(incident.getId())
                        .type(PagerDutyResourceType.INCIDENT.getName())
                        .status(PagerDutyIncidentStatus.ACKNOWLEDGED.getName()).build()
                ).collect(Collectors.toList());
        IncidentsDto incidentAcknowledgementPayload = new IncidentsDto(incidentsToBeAcknowledged);
        log.debug(MessageFormat.format("Acknowledging incidents {0}",
                incidentsToBeAcknowledged.stream().map(IncidentDto::getId).collect(Collectors.joining(","))));
        return pagerDutyClient.acknowledgeIncidents(incidentAcknowledgementPayload);
    }

    private void snoozeAcknowledgedIncidents(OnCallsDto onCalls, IncidentsDto acknowledgedIncidents) {
        OnCallDetailsDto onCallDetailsDto = onCalls.getOnCalls().stream().findFirst()
                .orElse(new OnCallDetailsDto(LocalDateTime.now(), LocalDateTime.now().plusHours(DEFAULT_SNOOZE_OFFSET)));
        LocalDateTime endOfShift = onCallDetailsDto.getEnd().plusMinutes(SNOOZE_MARGIN);

        long snoozeDurationInSeconds = ChronoUnit.SECONDS.between(LocalDateTime.now(), endOfShift);

        acknowledgedIncidents.getIncidents().forEach(incident -> {
            log.debug(MessageFormat.format("Snoozing incident {0} for {1} seconds",
                    incident.getId(), snoozeDurationInSeconds));
            pagerDutyClient.snoozeIncident(incident.getId(), new SnoozeDurationDto(snoozeDurationInSeconds));
        });
    }
}
