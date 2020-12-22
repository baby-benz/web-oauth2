package com.web.oauth.lab2.controller.exception;

import com.web.oauth.lab2.domain.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

/**
 * Предоставляет обработку исключений контроллеров всему сервису
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Обработчик исключения неверно переданного параметра запроса
     *
     * @param ex      Исключение
     * @param request Запрос
     * @return возвращаемая клиенту ResponseEntity
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleEntityExistsException(IllegalArgumentException ex, WebRequest request) {
        List<String> errors = Collections.singletonList("The server reported: " + ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Обработчик исключения отсутствующей в базе данных сущности
     *
     * @param ex      Исключение
     * @param request Запрос
     * @return возвращаемая клиенту ResponseEntity
     */
    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<ApiError> handleEntityExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Обработчик исключения аутентификации
     *
     * @param ex      Ошибка аутентификации
     * @param request Запрос
     * @return возвращаемая клиенту ResponseEntity
     */
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        return handleExceptionInternal(ex, new ApiError(errors), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Обработчик исключений по-умолчанию
     *
     * @param ex      Общее исключение
     * @param body    Список ошибок
     * @param headers HTTP заголовки
     * @param status  HTTP статус
     * @param request Запрос
     * @return возвращаемая клиенту ResponseEntity
     */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, @Nullable ApiError body,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
