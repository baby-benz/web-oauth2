package com.web.oauth.lab2.service.impl;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class Authorization {
    private final VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

    @Value("${security.oauth2.app-id}")
    private int appId;
    @Value("${security.oauth2.client-secret}")
    private String clientSecret;
    @Value("${security.oauth2.redirect-uri}")
    private String redirectUri;

    public Optional<UserAuthResponse> authorize(String code) {
        try {
            return Optional.of(
                    vk.oAuth()
                            .userAuthorizationCodeFlow(appId, clientSecret, redirectUri, code)
                            .execute()
            );
        } catch (ApiException e) {
            log.error("Business layer error (message: {}, description: {})", e.getMessage(), e.getDescription());
            return Optional.empty();
        } catch (ClientException e) {
            log.error("Error occurred while retrieving access token (message: {})", e.getMessage());
            return Optional.empty();
        }
    }
}
