package com.sw.output.admin.notice.converter;

import com.sw.output.admin.notice.dto.AdminNoticeResponseDTO;
import com.sw.output.domain.notice.entity.Notice;

public class AdminNoticeDTOConverter {
    public static AdminNoticeResponseDTO.NoticeDTO toNoticeDTO(Notice notice) {
        return AdminNoticeResponseDTO.NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}
