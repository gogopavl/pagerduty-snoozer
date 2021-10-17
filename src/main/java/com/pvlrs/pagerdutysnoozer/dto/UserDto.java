package com.pvlrs.pagerdutysnoozer.dto;

import com.pvlrs.pagerdutysnoozer.domain.redis.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserDto {

    private UserDetailDto user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDetailDto {

        private String id;
        private String name;

        public static UserDetailDto fromUser(User user) {
            return new UserDetailDto(user.getId(), user.getName());
        }
    }
}
