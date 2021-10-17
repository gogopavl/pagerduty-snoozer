package com.pvlrs.pagerdutysnoozer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OnCallsDto {

    @JsonProperty("oncalls")
    private List<OnCallDetailsDto> onCalls;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OnCallDetailsDto {

        private LocalDateTime start;
        private LocalDateTime end;
    }
}
