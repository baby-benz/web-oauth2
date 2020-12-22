package com.web.oauth.lab2.service.impl;

import com.web.oauth.lab2.service.impl.helpers.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public final class BrowserLauncher {
    private final UriBuilder uriBuilder;

    @PostConstruct
    private void launchBrowser() {
        System.setProperty("java.awt.headless", "false");

        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(uriBuilder.buildAuthorizationUri());
        } catch (IOException e) {
            log.error("Failed to launch client's default browser");
        }
    }
}
