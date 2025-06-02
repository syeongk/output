package com.sw.output.domain.notice.converter;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.domain.notice.entity.Notice;

public class NoticeDTOConverter {
    public static NoticeResponseDTO.NoticeDTO toNoticeDTO(Notice notice) {
        return NoticeResponseDTO.NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeResponseDTO.NoticeDetailDTO toNoticeDetailDTO(Notice notice) {
        return NoticeResponseDTO.NoticeDetailDTO.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .createdAt(notice.getCreatedAt())
                .build();
    }
}