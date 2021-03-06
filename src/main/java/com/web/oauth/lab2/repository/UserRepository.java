package com.web.oauth.lab2.repository;

import com.web.oauth.lab2.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User getUserById(long id);
    Optional<User> findUserByUserId(int userId);
    Optional<User> findUserById(long id);
}
