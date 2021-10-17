package com.pvlrs.pagerdutysnoozer.service;

import com.pvlrs.pagerdutysnoozer.domain.redis.User;

import java.util.List;

public interface UserService {

    Iterable<User> getAllUsers();

    User saveUser(User user);

    void saveUsers(List<User> users);

    void removeUsers(List<User> users);
}
