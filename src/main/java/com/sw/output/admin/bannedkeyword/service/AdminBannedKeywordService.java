package com.sw.output.admin.bannedkeyword.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sw.output.admin.bannedkeyword.converter.AdminBannedKeywordConverter;
import com.sw.output.admin.bannedkeyword.dto.AdminBannedKeywordRequestDTO;
import com.sw.output.domain.bannedkeyword.entity.BannedKeyword;
import com.sw.output.domain.bannedkeyword.repository.BannedKeywordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBannedKeywordService {
    private final BannedKeywordRepository bannedKeywordRepository;

    public List<BannedKeyword> getBannedKeywords() {
        return bannedKeywordRepository.findAll();
    }

    @Transactional
    public void createBannedKeyword(AdminBannedKeywordRequestDTO.BannedKeywordDTO request) {
        BannedKeyword bannedKeyword = AdminBannedKeywordConverter.toBannedKeyword(request);
        bannedKeywordRepository.save(bannedKeyword);
    }
}
