package com.web.oauth.lab2.service.impl.repository;

import com.web.oauth.lab2.controller.exception.EntityAlreadyExistsException;
import com.web.oauth.lab2.controller.exception.IdType;
import com.web.oauth.lab2.domain.entity.User;
import com.web.oauth.lab2.repository.UserRepository;
import com.web.oauth.lab2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserRepositoryService implements UserService {
    private final UserRepository userRepository;

    @Override
    public Optional<User> findUser(int userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public Optional<User> findUser(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User updateUser(User user) throws EntityAlreadyExistsException {
        int userId = user.getUserId();
        Optional<User> userWithSameUserId = userRepository.findUserByUserId(userId);
        User userToSave;
        if (userWithSameUserId.isPresent()) {
            userToSave = userWithSameUserId.get();
            if (!userToSave.getId().equals(user.getId())) {
                throw EntityAlreadyExistsException.createWith(userId, IdType.USER_ID);
            } else {
                if (userRepository.existsById(user.getId())) {
                    userToSave.setUserId(userId);
                    userToSave.setFullName(user.getFullName());
                    return userRepository.save(userToSave);
                } else {
                    throw new IllegalArgumentException("wrong id passed");
                }
            }
        } else {
            if (userRepository.existsById(user.getId())) {
                userToSave = userRepository.getOne(user.getId());
                userToSave.setUserId(userId);
                userToSave.setFullName(user.getFullName());
                return userRepository.save(userToSave);
            } else {
                throw new IllegalArgumentException("wrong id passed");
            }
        }
    }

    @Override
    public User saveUser(User user) throws EntityAlreadyExistsException {
        int userId = user.getUserId();
        if (userRepository.findUserByUserId(userId).isPresent()) {
            throw EntityAlreadyExistsException.createWith(userId, IdType.USER_ID);
        } else {
            try {
                long dbId = user.getId();
                if (userRepository.existsById(dbId)) {
                    throw EntityAlreadyExistsException.createWith(dbId, IdType.DB_ID);
                }
                return userRepository.save(user);
            } catch (InvalidDataAccessApiUsageException | NullPointerException e) {
                return userRepository.save(user);
            }
        }
    }
}
