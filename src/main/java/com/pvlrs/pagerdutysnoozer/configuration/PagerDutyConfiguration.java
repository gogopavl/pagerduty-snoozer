package com.pvlrs.pagerdutysnoozer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "com.pagerduty")
public class PagerDutyConfiguration {

    private String host;
    private String token;
}
