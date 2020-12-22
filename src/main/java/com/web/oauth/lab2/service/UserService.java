package com.web.oauth.lab2.service;

import com.web.oauth.lab2.controller.exception.EntityAlreadyExistsException;
import com.web.oauth.lab2.domain.entity.User;

import java.util.Optional;

public interface UserService {
    User updateUser(User user) throws EntityAlreadyExistsException;

    User saveUser(User user) throws EntityAlreadyExistsException;

    Optional<User> findUser(int userId);

    Optional<User> findUser(long id);
}
