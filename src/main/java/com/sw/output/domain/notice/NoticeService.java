package com.sw.output.domain.notice;

import com.sw.output.domain.notice.converter.NoticeDTOConverter;
import com.sw.output.domain.notice.dto.NoticeResponseDTO;
import com.sw.output.domain.notice.entity.Notice;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.NoticeErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.sw.output.domain.notice.converter.NoticeDTOConverter.toNoticeDetailDTO;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public List<NoticeResponseDTO.NoticeDTO> getNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(NoticeDTOConverter::toNoticeDTO)
                .collect(Collectors.toList());
    }

    public NoticeResponseDTO.NoticeDetailDTO getNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));
        return toNoticeDetailDTO(notice);
    }
}
