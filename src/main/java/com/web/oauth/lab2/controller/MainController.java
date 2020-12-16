package com.web.oauth.lab2.controller;

import com.google.gson.JsonElement;
import com.web.oauth.lab2.controller.exception.AuthenticationException;
import com.web.oauth.lab2.service.contoller.MainControllerService;
import com.web.oauth.lab2.service.helpers.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping
    @ResponseBody
    public JsonElement getUserInfo(@RequestParam String code) throws AuthenticationException {
        if (code == null) {
            throw AuthenticationException.createWith("code is not received");
        }

        JsonElement element = mainControllerService.getMainInfo(code);

        log.info("Returned user info: {}", element);

        return element;
    }

    @GetMapping("/info")
    public RedirectView getUserInfo() {
        return new RedirectView(uriBuilder.buildAuthorizationUri().toString());
    }
}
