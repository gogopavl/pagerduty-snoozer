package com.pvlrs.pagerdutysnoozer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentsDto {

    private List<IncidentDto> incidents;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IncidentDto {

        private String id;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String title;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String description;

        private String status;

        private String type;
    }
}
