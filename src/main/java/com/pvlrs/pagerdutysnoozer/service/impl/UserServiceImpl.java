package com.pvlrs.pagerdutysnoozer.service.impl;

import com.pvlrs.pagerdutysnoozer.domain.redis.User;
import com.pvlrs.pagerdutysnoozer.repository.UserRepository;
import com.pvlrs.pagerdutysnoozer.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record UserServiceImpl(UserRepository userRepository) implements UserService {

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void saveUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    @Override
    public void removeUsers(List<User> users) {
        userRepository.deleteAll(users);
    }
}
