package com.sw.output.domain.member.converter;

import com.sw.output.domain.interviewset.projection.InterviewSetSummaryProjection;
import com.sw.output.domain.member.dto.MyPageResponseDTO;
import com.sw.output.global.dto.CommonResponseDTO;

import java.util.List;

import static com.sw.output.global.converter.CommonConverter.toCursorDTO;

public class MyPageConverter {
    public static MyPageResponseDTO.BookmarkedInterviewSetsDTO toBookmarkedInterviewSetsDTO(List<InterviewSetSummaryProjection> interviewSets, InterviewSetSummaryProjection lastInterviewSet) {
        CommonResponseDTO.CursorDTO nextCursor = null;

        if (lastInterviewSet != null) {
            nextCursor = toCursorDTO(lastInterviewSet.getId(), lastInterviewSet.getCreatedAt());
        }

        return MyPageResponseDTO.BookmarkedInterviewSetsDTO.builder()
                .bookmarkedInterviewSets(interviewSets)
                .cursorDTO(nextCursor)
                .build();
    }
}
