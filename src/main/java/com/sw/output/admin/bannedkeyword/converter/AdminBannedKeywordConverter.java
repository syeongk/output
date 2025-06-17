package com.sw.output.admin.bannedkeyword.converter;

import com.sw.output.admin.bannedkeyword.dto.AdminBannedKeywordRequestDTO;
import com.sw.output.domain.bannedkeyword.entity.BannedKeyword;

public class AdminBannedKeywordConverter {
    public static BannedKeyword toBannedKeyword(AdminBannedKeywordRequestDTO.BannedKeywordDTO request) {
        return BannedKeyword.builder()
                .keyword(request.getKeyword())
                .build();
    }
}
