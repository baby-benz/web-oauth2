package com.web.oauth.lab2.controller;

import com.web.oauth.lab2.controller.exception.AuthenticationException;
import com.web.oauth.lab2.controller.exception.EntityAlreadyExistsException;
import com.web.oauth.lab2.service.impl.contoller.MainControllerService;
import com.web.oauth.lab2.service.impl.helpers.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MainController {
    private final MainControllerService mainControllerService;
    private final UriBuilder uriBuilder;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String getUserInfo(@RequestParam String code) throws AuthenticationException, EntityAlreadyExistsException {
        if (code == null) {
            throw AuthenticationException.createWith("code is not received");
        }

        String fullName = mainControllerService.getMainInfo(code);

        log.info("Returned user info: {}", fullName);

        return fullName;
    }

    @GetMapping("/info")
    public RedirectView getUserInfo() {
        return new RedirectView(uriBuilder.buildAuthorizationUri().toString());
    }
}
