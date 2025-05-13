package com.sw.output.domain.auth.dto;

import lombok.Getter;

public class GoogleOAuthDTO {
    @Getter
    public static class GoogleAccessTokenDTO {
        private String access_token;
        private Integer expires_in;
        private String id_token;
        private String refresh_token;
        private String scope;
        private String token_type;
    }

    @Getter
    public static class GoogleUserInfoDTO {
        private String id;
        private String email;
        private Boolean verified_email;
        private String picture;
    }
}
