package com.sw.output.domain.auth.controller;

import com.sw.output.domain.auth.dto.AuthResponseDTO;
import com.sw.output.domain.auth.dto.GoogleOAuthDTO;
import com.sw.output.domain.auth.service.AuthService;
import com.sw.output.domain.auth.service.OAuthService;
import com.sw.output.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "API 요청 시 토큰 불필요")
public class AuthController {
    private final OAuthService oAuthService;
    private final AuthService authService;

    @PostMapping("social-login")
    @Operation(summary = "소셜 로그인 API", description = "Google에서 발급받은 ID Token을 통해 로그인")
    public ApiResponse<AuthResponseDTO.TokenDTO> socialLogin(@RequestParam String idToken) {
        AuthResponseDTO.TokenDTO tokenDTO = oAuthService.socialLogin(idToken);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("reissue")
    @Operation(summary = "토큰 재발급 API", description = "로그인 시 얻은 Refresh token을 통해 Access token, Refresh token 재발급")
    public ApiResponse<AuthResponseDTO.TokenDTO> reissue(
            @RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponseDTO.TokenDTO tokenDTO = authService.reissue(refreshToken);
        return ApiResponse.success(tokenDTO);
    }

    @PostMapping("test")
    @Operation(summary = "구글 로그인 테스트 API", description = "백엔드에서 로그인 테스트 용도로 사용")
    public ApiResponse<GoogleOAuthDTO.GoogleAccessTokenDTO> test(@RequestParam String code) {
        GoogleOAuthDTO.GoogleAccessTokenDTO response = oAuthService.test(code);
        return ApiResponse.success(response);
    }

    @PostMapping("logout")
    @Operation(summary = "로그아웃 API", description = "DB에 저장된 Refresh token을 삭제")
    public ApiResponse<Void> logout() {
        authService.logout();
        return ApiResponse.success();
    }
}
