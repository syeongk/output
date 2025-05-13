package com.sw.output.domain.interviewset.converter;

import com.sw.output.domain.interviewset.entity.Bookmark;
import com.sw.output.domain.interviewset.entity.InterviewSet;
import com.sw.output.domain.member.entity.Member;

public class BookmarkConverter {
    public static Bookmark toBookmark(InterviewSet interviewSet, Member member) {
        return Bookmark.builder()
                .interviewSet(interviewSet)
                .member(member)
                .build();
    }
}
