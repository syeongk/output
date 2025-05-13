package com.sw.output.domain.auth.controller;

import com.sw.output.domain.auth.dto.AuthResponseDTO;
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
    public ApiResponse<AuthResponseDTO.TokenDTO> socialLogin(@RequestParam String code) {
        AuthResponseDTO.TokenDTO tokenDTO = oAuthService.socialLogin(code);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("reissue")
    public ApiResponse<Void> reissue() {
        return ApiResponse.success();
    }
}
