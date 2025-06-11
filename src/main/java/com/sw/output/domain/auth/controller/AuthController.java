package com.sw.output.domain.auth.controller;

import com.sw.output.domain.auth.dto.AuthResponseDTO;
import com.sw.output.domain.auth.dto.GoogleOAuthDTO;
import com.sw.output.domain.auth.service.AuthService;
import com.sw.output.domain.auth.service.OAuthService;
import com.sw.output.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final OAuthService oAuthService;
    private final AuthService authService;

    @PostMapping("social-login")
    public ApiResponse<AuthResponseDTO.TokenDTO> socialLogin(@RequestParam String idToken) {
        AuthResponseDTO.TokenDTO tokenDTO = oAuthService.socialLogin(idToken);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("reissue")
    public ApiResponse<AuthResponseDTO.TokenDTO> reissue(
            @RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponseDTO.TokenDTO tokenDTO = authService.reissue(refreshToken);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("test")
    public ApiResponse<GoogleOAuthDTO.GoogleAccessTokenDTO> test(@RequestParam String code) {
        GoogleOAuthDTO.GoogleAccessTokenDTO response = oAuthService.test(code);
        return ApiResponse.success(response);
    }

    @PostMapping("logout")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.success();
    }
}
