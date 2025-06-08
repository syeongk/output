package com.sw.output.domain.notice.converter;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.domain.notice.entity.Notice;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.global.converter.CommonConverter.toCursorDTO;

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

    public static NoticeResponseDTO.NoticesDTO toNoticesDTO(List<Notice> notices, Notice lastNotice) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastNotice != null) {
            nextCursor = toCursorDTO(lastNotice.getId(), lastNotice.getCreatedAt());
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
