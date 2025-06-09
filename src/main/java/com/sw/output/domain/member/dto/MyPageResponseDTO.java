package com.sw.output.domain.member.dto;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.global.dto.CommonResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MyPageResponseDTO {
    @Getter
    @Builder
    public static class MyInterviewSetsDTO {
        private List<InterviewSetSummaryProjection> myInterviewSets;
        private CommonResponseDTO.CursorDTO cursorDTO;
    }

    @Getter
    @Builder
    public static class BookmarkedInterviewSetsDTO {
        private List<InterviewSetSummaryProjection> bookmarkedInterviewSets;
        private CommonResponseDTO.CursorDTO cursorDTO;
    }
}
