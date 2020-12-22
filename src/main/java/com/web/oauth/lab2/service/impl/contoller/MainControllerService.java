package com.web.oauth.lab2.service.impl.contoller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.web.oauth.lab2.controller.exception.AuthenticationException;
import com.web.oauth.lab2.controller.exception.EntityAlreadyExistsException;
import com.web.oauth.lab2.domain.entity.User;
import com.web.oauth.lab2.service.UserService;
import com.web.oauth.lab2.service.impl.Authorization;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainControllerService {
    private final Authorization authorization;
    private final UserService userService;
    private final VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

    public String getMainInfo(String code) throws AuthenticationException, EntityAlreadyExistsException {
        UserAuthResponse authResponse = authorization.authorize(code).orElseThrow(
                () -> AuthenticationException.createWith("unable to retrieve user data")
        );
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        Optional<User> user = userService.findUser(actor.getId());

        if(user.isPresent()) {
            return user.get().getFullName();
        } else {
            try {
                JsonElement element = vk.execute().code(actor,
                        "var response = API.users.get({\"user_id\":" + actor.getId() + "});" +
                                "return response@.first_name + response@.last_name;"
                ).execute();

                JsonArray jsonArray = element.getAsJsonArray();

                StringBuilder builder = new StringBuilder();

                for (int i = 0; i < 2; i++) {
                    String namePart = jsonArray.get(i).toString();
                    builder.append(namePart, 1, namePart.length() - 1);
                    if (i == 0) {
                        builder.append(" ");
                    }
                }

                return userService.saveUser(new User(actor.getId(), builder.toString())).getFullName();
            } catch (ApiException | ClientException e) {
                throw AuthenticationException.createWith("unable to retrieve user main info");
            }
        }
    }
}
