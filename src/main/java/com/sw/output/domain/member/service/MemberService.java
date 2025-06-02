package com.sw.output.domain.member.service;

import com.sw.output.domain.member.dto.MemberRequestDTO;
import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.member.repository.MemberRepository;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.MemberErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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
        String nickname = updateNicknameDTO.getNickname();

        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new BusinessException(MemberErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        member.updateNickname(nickname);
    }

    @Transactional
    public void deleteMember() {
        // TODO : 멤버 조회 AOP 추가, 1번으로 하드코딩
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.softDelete();
    }
}
