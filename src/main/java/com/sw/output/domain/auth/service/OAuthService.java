package com.sw.output.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.sw.output.domain.auth.converter.AuthDTOConverter;
import com.sw.output.domain.auth.dto.AuthResponseDTO;
import com.sw.output.domain.auth.dto.GoogleOAuthDTO;
import com.sw.output.domain.auth.entity.RefreshToken;
import com.sw.output.domain.auth.repository.RefreshTokenRepository;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.AuthErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;

import static com.sw.output.domain.auth.converter.RefreshTokenConverter.toRefreshToken;
import static com.sw.output.domain.member.converter.MemberConverter.toMember;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RestTemplate restTemplate;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.security.oauth2.client.google.web.client-id}")
    private String webClientId;
    @Value("${spring.security.oauth2.client.google.web.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.google.web.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.google.web.grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.google.android.client-id}")
    private String androidClientId;

    /**
     * 구글 액세스 토큰 요청
     */
    public GoogleOAuthDTO.GoogleAccessTokenDTO test(String code) {
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        // 요청 본문 데이터 구성
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grantType);
        requestBody.add("client_id", webClientId);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("code", decodedCode);
        requestBody.add("client_secret", clientSecret);

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        // 구글 OAuth 서버에 액세스 토큰 요청
        GoogleOAuthDTO.GoogleAccessTokenDTO googleAccessTokenDTO = restTemplate.postForObject(
                "https://oauth2.googleapis.com/token",
                request,
                GoogleOAuthDTO.GoogleAccessTokenDTO.class);

        log.info("googleAccessTokenDTO : {}", googleAccessTokenDTO.getId_token());
        return googleAccessTokenDTO;
    }

    /**
     * Google ID Token 검증
     *
     * @param idToken Google에서 받은 ID Token
     * @return 검증된 토큰의 payload에서 이메일 추출
     * @throws GeneralSecurityException 토큰 검증 실패시
     * @throws IOException              네트워크 오류 발생시
     */
    private Payload verifyGoogleIdToken(String idToken) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Arrays.asList(webClientId, androidClientId))
                .build();
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken); // 유효한 서명, 토큰 만료, 발급자 Google, 수신자 clientId
            if (googleIdToken == null) {
                throw new BusinessException(AuthErrorCode.INVALID_ID_TOKEN);
            }
            return googleIdToken.getPayload();

        } catch (GeneralSecurityException | IOException e) {
            throw new BusinessException(AuthErrorCode.GOOGLE_VERIFICATION_FAILED);
        }
    }

    /**
     * Google ID 토큰으로 소셜 로그인 처리
     */
    @Transactional
    public AuthResponseDTO.TokenDTO socialLogin(String idToken) {
        Payload payload = verifyGoogleIdToken(idToken);
        String email = payload.getEmail();
        String picture = (String) payload.getOrDefault("picture", null);

        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(toMember(email, picture))); // 이메일 없으면 회원 가입

        // 로그인 처리 (Access token, Refresh token 발급)
        String accessToken = jwtTokenProvider.createAccessToken(email);
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        // refresh token 업데이트 또는 저장
        LocalDateTime expire = jwtTokenProvider.parseClaims(refreshToken)
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByMemberId(member.getId());
        if (refreshTokenOptional.isPresent()) {
            refreshTokenOptional.get().updateToken(refreshToken, expire);
        } else {
            RefreshToken newRefreshTokenEntity = toRefreshToken(refreshToken, expire, member);
            refreshTokenRepository.save(newRefreshTokenEntity);
        }

        return AuthDTOConverter.toTokenDTO(accessToken, refreshToken);
    }
}
