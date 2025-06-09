package com.sw.output.domain.auth.controller;

import com.sw.output.domain.auth.dto.AuthResponseDTO;
import com.sw.output.domain.auth.dto.GoogleOAuthDTO;
import com.sw.output.domain.auth.service.OAuthService;
import com.sw.output.domain.auth.service.TokenService;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuthService oAuthService;
    private final TokenService tokenService;

    @PostMapping("social-login")
    public ApiResponse<AuthResponseDTO.TokenDTO> socialLogin(@RequestParam String idToken) {
        AuthResponseDTO.TokenDTO tokenDTO = oAuthService.socialLogin(idToken);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("reissue")
    public ApiResponse<Void> reissue() {
        return ApiResponse.success();
    }

    @PostMapping("test")
    public ApiResponse<GoogleOAuthDTO.GoogleAccessTokenDTO> test(@RequestParam String code) {
        GoogleOAuthDTO.GoogleAccessTokenDTO response = oAuthService.test(code);
        return ApiResponse.success(response);
    }
}
