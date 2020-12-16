package com.web.oauth.lab2.service.contoller;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.web.oauth.lab2.controller.exception.AuthenticationException;
import com.web.oauth.lab2.service.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainControllerService {
    private final Authorization authorization;
    private final VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

    public JsonElement getMainInfo(String code) throws AuthenticationException {
        UserAuthResponse authResponse = authorization.authorize(code).orElseThrow(
                () -> AuthenticationException.createWith("unable to retrieve user data")
        );
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        try {
            return vk.execute().code(actor,
                    "var response = API.users.get({\"user_id\":" + actor.getId() + "});" +
                            "return response@.first_name + response@.last_name;"
            ).execute();
        } catch (ApiException | ClientException e) {
            throw AuthenticationException.createWith("unable to retrieve user main info");
        }
    }
}
