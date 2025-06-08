package com.sw.output.domain.member.repository;

import com.sw.output.domain.member.entity.Member;
import com.sw.output.domain.notice.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    @Query("""
            SELECT n
            FROM Notice n
            WHERE (n.createdAt < :cursorCreatedAt OR (n.createdAt = :cursorCreatedAt AND n.id < :cursorId))
            ORDER BY n.createdAt DESC, n.id DESC
            """)
    Slice<Notice> findNoticeNextPage(Pageable pageable, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt
    );
}
