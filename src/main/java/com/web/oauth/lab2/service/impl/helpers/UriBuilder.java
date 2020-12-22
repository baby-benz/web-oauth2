package com.web.oauth.lab2.service.impl.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class UriBuilder {
    @Value("${security.oauth2.authorization.base-uri}")
    private String authorizationUri;
    @Value("#{${security.oauth2.authorization.query-params}}")
    private Map<String, String> authorizationQueryParams;

    private static final String BASE_URI = "oauth.vk.com";

    public URI buildAuthorizationUri() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(BASE_URI)
                .path(authorizationUri);
        authorizationQueryParams.forEach(uriComponentsBuilder::queryParam);

        return uriComponentsBuilder.build().toUri();
    }
}
