package com.sw.output.domain.notice;

import com.sw.output.domain.notice.dto.NoticeRequestDTO;
import com.sw.output.domain.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sw.output.domain.notice.converter.NoticeConverter.toNotice;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public void createNotice(NoticeRequestDTO.NoticeDTO request) {
        Notice notice = toNotice(request);
        noticeRepository.save(notice);
    }

    public void getNotices() {
    }

    public void getNotice(Long noticeId) {
    }

    public void updateNotice(Long noticeId, NoticeRequestDTO.NoticeDTO request) {
    }

    public void deleteNotice(Long noticeId) {
    }
}
