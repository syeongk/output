package com.sw.output.domain.auth.service;

import com.sw.output.domain.auth.dto.OAuthResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {
    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.google.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.google.grant-type}")
    private String grantType;

    /**
     * 구글 액세스 토큰 요청
     */
    public String getGoogleAccessToken(String code) {
        // 요청 본문 데이터 구성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("client_id", clientId);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", code);
        requestBody.add("client_secret", clientSecret);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);


        // 구글 OAuth 서버에 액세스 토큰 요청
        OAuthResponseDTO.GoogleAccessTokenDTO googleAccessTokenDTO = restTemplate.postForObject(
                "https://oauth2.googleapis.com/token",
                request,
                OAuthResponseDTO.GoogleAccessTokenDTO.class
        );

        return googleAccessTokenDTO.getAccess_token();
    }

    /**
     * 액세스 토큰으로 사용자 정보 요청
     */
    public String getGoogleEmail(String accessToken) {
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        // HTTP 요청 엔티티 생성
        HttpEntity<Void> request = new HttpEntity<>(headers);

        // 구글 API 서버에 사용자 이메일 정보 요청
        OAuthResponseDTO.GoogleUserInfoDTO googleUserInfoDTO = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                request,
                OAuthResponseDTO.GoogleUserInfoDTO.class
        ).getBody();

        return googleUserInfoDTO.getEmail();
    }

    /**
     * 1. 구글 액세스 토큰 요청
     * <br>
     * 2. 액세스 토큰으로 사용자 이메일 정보 요청
     */
    public String socialLogin(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);
        String googleAccessToken = getGoogleAccessToken(decodedCode);
        String email = getGoogleEmail(googleAccessToken);

        return email;
    }
}
