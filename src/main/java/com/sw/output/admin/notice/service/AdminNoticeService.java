package com.sw.output.admin.notice.service;

import static com.sw.output.admin.notice.converter.AdminNoticeConverter.toNotice;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sw.output.admin.notice.dto.AdminNoticeRequestDTO;
import com.sw.output.domain.notice.NoticeRepository;
import com.sw.output.domain.notice.entity.Notice;
import com.sw.output.global.exception.BusinessException;
import com.sw.output.global.response.errorcode.CommonErrorCode;
import com.sw.output.global.response.errorcode.NoticeErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminNoticeService {
    private final NoticeRepository noticeRepository;

    public Notice getNoticeById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));
    }

    public List<Notice> getNotices() {
        return noticeRepository.findAll();
    }

    @Transactional
    public void createNotice(AdminNoticeRequestDTO.NoticeDTO request) {
        Notice notice = toNotice(request);
        noticeRepository.save(notice);
    }
    
    @Transactional
    public void deleteNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));

        if (notice.getIsDeleted()) {
            throw new BusinessException(CommonErrorCode.ALREADY_DELETED);
        }

        notice.softDelete();
    }

    @Transactional
    public void restoreNotice(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));

        if (!notice.getIsDeleted()) {
            throw new BusinessException(CommonErrorCode.NOT_DELETED);
        }

        notice.restore();
    }

    @Transactional
    public void updateNotice(Long noticeId, AdminNoticeRequestDTO.NoticeDTO request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(NoticeErrorCode.NOTICE_NOT_FOUND));

        notice.update(request);
    }
}
