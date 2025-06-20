package com.sw.output.admin.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sw.output.domain.admin.Admin;
import com.sw.output.domain.admin.AdminRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.AuthErrorCode;
import com.sw.output.global.response.errorcode.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAuthService {
    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Admin validateLogin(AdminRequestDTO.LoginDTO request) {
        Admin admin = adminRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new BusinessException(AuthErrorCode.INVALID_CREDENTIALS);
        }

        return admin;
    }
}
