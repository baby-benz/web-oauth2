package com.web.oauth.lab2.controller.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends Exception {
    private final long id;
    private final IdType idType;

    public static EntityAlreadyExistsException createWith(long id, IdType idType) {
        return new EntityAlreadyExistsException(id, idType);
    }

    @Override
    public String getMessage() {
        return String.format("The server reported: user with %s %d already exists.", idType.toString(), id);
    }
}
