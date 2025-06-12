package com.sw.output.domain.interviewset.projection;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface InterviewSetSummaryProjection {
    Long getId();

    String getTitle();

    Integer getBookmarkCount();

    Integer getMockCount();

    Boolean getIsAnswerPublic();

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime getCreatedAt();

    MemberInfoProjection getMember();

    BookmarkInfoProjection getBookmark();

    interface MemberInfoProjection {
        String getNickname();
    }

    interface BookmarkInfoProjection {
        Long getId();
    }
}
