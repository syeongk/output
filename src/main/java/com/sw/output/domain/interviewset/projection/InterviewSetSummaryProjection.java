package com.sw.output.domain.interviewset.projection;

import java.time.LocalDateTime;

public interface InterviewSetSummaryProjection {
    Long getId();

    String getTitle();

    Integer getBookmarkCount();

    LocalDateTime getCreatedAt();

    MemberInfoProjection getMember();

    interface MemberInfoProjection {
        String getNickname();
    }
}
