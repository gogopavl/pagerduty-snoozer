package com.pvlrs.pagerdutysnoozer.domain.redis;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@Builder
@RedisHash("User")
public class User implements Serializable {

    private String id;
    private String name;
    private String incidentNote;
}
