package com.sw.output.admin.notice.converter;

import com.sw.output.admin.notice.dto.AdminNoticeRequestDTO;
import com.sw.output.domain.notice.entity.Notice;
import org.springframework.stereotype.Component;

@Component
public class AdminNoticeConverter {
    public static Notice toNotice(AdminNoticeRequestDTO.NoticeDTO request) {
        return Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
