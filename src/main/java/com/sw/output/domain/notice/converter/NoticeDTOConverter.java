package com.sw.output.domain.notice.converter;

import java.util.List;
import java.util.stream.Collectors;

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

    public static NoticeResponseDTO.NoticeCursorDTO toNoticeCursorDTO(Notice notice) {
        return NoticeResponseDTO.NoticeCursorDTO.builder()
                .id(notice.getId())
                .createdAt(notice.getCreatedAt())
                .build();
    }

    public static NoticeResponseDTO.NoticesDTO toNoticesDTO(List<Notice> notices, Notice lastNotice) {
        NoticeResponseDTO.NoticeCursorDTO nextCursor = null;

        if (lastNotice != null) {
            nextCursor = toNoticeCursorDTO(lastNotice);
        }

        List<NoticeResponseDTO.NoticeDTO> noticeDTOs = notices.stream()
                .map(NoticeDTOConverter::toNoticeDTO)
                .collect(Collectors.toList());

        return NoticeResponseDTO.NoticesDTO.builder()
                .notices(noticeDTOs)
                .nextCursor(nextCursor)
                .build();
    }
}