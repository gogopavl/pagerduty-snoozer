package com.pvlrs.pagerdutysnoozer.repository;

import com.pvlrs.pagerdutysnoozer.domain.redis.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
