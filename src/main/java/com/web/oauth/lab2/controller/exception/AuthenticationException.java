package com.web.oauth.lab2.controller.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Authentication code is not retrieved.")
public class AuthenticationException extends Exception {
    private final String cause;

    public static AuthenticationException createWith(String cause) {
        return new AuthenticationException(cause);
    }

    @Override
    public String getMessage() {
        return String.format("The server reported: %s.", cause);
    }
}
