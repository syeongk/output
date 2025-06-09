package com.sw.output.domain.notice;

import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.domain.notice.entity.Notice;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.NoticeErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.sw.output.domain.notice.converter.NoticeDTOConverter.toNoticeDetailDTO;
import static com.sw.output.domain.notice.converter.NoticeDTOConverter.toNoticesDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeResponseDTO.NoticesDTO getNotices(Long cursorId, LocalDateTime cursorCreatedAt, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);

        Slice<Notice> noticesSlice;
        if (cursorId == null || cursorCreatedAt == null) {
            noticesSlice = noticeRepository.findNoticeFirstPage(pageable);
        } else {
            noticesSlice = noticeRepository.findNoticeNextPage(pageable, cursorId, cursorCreatedAt);
        }

        if (noticesSlice.isEmpty()) {
            return toNoticesDTO(new ArrayList<>(), null);
        }

        List<Notice> notices = noticesSlice.getContent();
        if (!noticesSlice.hasNext()) {
            return toNoticesDTO(notices, null);
        } else {
            Notice lastNotice = notices.get(notices.size() - 1);
            return toNoticesDTO(notices, lastNotice);
        }
    }

    public NoticeResponseDTO.NoticeDetailDTO getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));
        return toNoticeDetailDTO(notice);
    }
}
