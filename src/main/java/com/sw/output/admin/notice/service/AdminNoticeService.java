package com.sw.output.admin.notice.service;

import com.sw.output.admin.notice.dto.AdminNoticeRequestDTO;
import com.sw.output.domain.notice.NoticeRepository;
import com.sw.output.domain.notice.entity.Notice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.sw.output.admin.notice.converter.AdminNoticeConverter.toNotice;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {
    private final NoticeRepository noticeRepository;

    public void createNotice(AdminNoticeRequestDTO.NoticeDTO request) {
        Notice notice = toNotice(request);
        noticeRepository.save(notice);
    }

    public List<Notice> getNotices() {
        return noticeRepository.findAll();
    }

}
