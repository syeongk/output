package com.sw.output.domain.auth.dto;

import lombok.Getter;

public class OAuthResponseDTO {
    @Getter
    public static class GoogleAccessTokenDTO {
        String access_token;
        Integer expires_in;
        String id_token;
        String refresh_token;
        String scope;
        String token_type;
    }

    @Getter
    public static class GoogleUserInfoDTO {
        String id;
        String email;
        Boolean verified_email;
        String picture;

    }
}
