package com.sw.output.domain.member.service;

import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.sw.output.global.util.SecurityUtils.getAuthenticatedUsername;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 닉네임 수정
     *
     * @param updateNicknameDTO 닉네임 수정 요청
     * @throws BusinessException 회원을 찾을 수 없거나, 닉네임이 이미 존재할 경우
     */
    @Transactional
    public void updateNickname(MemberRequestDTO.UpdateNicknameDTO updateNicknameDTO) {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        String newNickname = updateNicknameDTO.getNickname();

        if (memberRepository.findByNickname(newNickname).isPresent()) {
            throw new BusinessException(MemberErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        member.updateNickname(newNickname);
    }

    @Transactional
    public void deleteMember() {
        Member member = memberRepository.findByEmail(getAuthenticatedUsername())
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.softDelete();
    }
}
