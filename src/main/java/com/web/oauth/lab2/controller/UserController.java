package com.web.oauth.lab2.controller;

import com.web.oauth.lab2.controller.exception.EntityAlreadyExistsException;
import com.web.oauth.lab2.controller.exception.IdType;
import com.web.oauth.lab2.domain.entity.User;
import com.web.oauth.lab2.service.impl.repository.UserRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepositoryService userRepositoryService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user) throws EntityAlreadyExistsException {
        checkUserProperties(user);
        return userRepositoryService.saveUser(user);
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(@PathVariable long id) {
        return userRepositoryService.findUser(id).orElse(null);
    }

    @RequestMapping(value = "/user", method = {RequestMethod.PUT, RequestMethod.PATCH}, consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody User user) throws EntityAlreadyExistsException {
        checkUserProperties(user);
        return userRepositoryService.updateUser(user);
    }

    private void checkUserProperties(User user) {
        if (user.getId() == null || user.getId() == 0) {
            throw new IllegalArgumentException("wrong " + IdType.DB_ID.toString() + " is passed.");
        }
        if (user.getUserId() == 0) {
            throw new IllegalArgumentException("wrong " + IdType.USER_ID.toString() + " is passed.");
        }
    }
}
