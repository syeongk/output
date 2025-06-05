package com.sw.output.domain.notice;

import com.sw.output.domain.notice.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("""
            SELECT n
            FROM Notice n
            ORDER BY n.createdAt DESC, n.id DESC
            """)
    Slice<Notice> findFirstPage(Pageable pageable);

    @Query("""
            SELECT n
            FROM Notice n
            WHERE (n.createdAt < :cursorCreatedAt OR (n.createdAt = :cursorCreatedAt AND n.id < :cursorId))
            ORDER BY n.createdAt DESC, n.id DESC
            """)
    Slice<Notice> findNextPage(Pageable pageable, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt
    );
}
