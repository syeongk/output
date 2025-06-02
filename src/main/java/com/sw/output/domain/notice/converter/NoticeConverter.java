package com.sw.output.domain.notice.converter;

import com.sw.output.domain.notice.dto.NoticeRequestDTO;
import com.sw.output.domain.notice.entity.Notice;
import org.springframework.stereotype.Component;

@Component
public class NoticeConverter {
    public static Notice toNotice(NoticeRequestDTO.NoticeDTO request) {
        return Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
