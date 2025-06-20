package com.sw.output.admin.auth;

import lombok.Getter;
import lombok.Setter;

public class AdminRequestDTO {

    @Getter
    @Setter
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
