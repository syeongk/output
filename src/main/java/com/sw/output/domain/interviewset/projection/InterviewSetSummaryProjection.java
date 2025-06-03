package com.sw.output.domain.interviewset.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface InterviewSetSummaryProjection {
    Long getId();

    String getTitle();

    Integer getBookmarkCount();

    Boolean getIsAnswerPublic();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getCreatedAt();

    MemberInfoProjection getMember();

    interface MemberInfoProjection {
        String getNickname();
    }
}
