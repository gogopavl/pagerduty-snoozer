package com.pvlrs.pagerdutysnoozer.client;

import com.pvlrs.pagerdutysnoozer.configuration.PagerDutyConfiguration;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public record PagerDutyClientInterceptor(PagerDutyConfiguration pagerDutyConfiguration) implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Optional.ofNullable(pagerDutyConfiguration.getToken())
                .ifPresent(token -> requestTemplate.header(AUTHORIZATION_HEADER, token.trim()));
    }
}
