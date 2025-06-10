package com.sw.output.domain.faq.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sw.output.domain.faq.entity.Faq;
import com.sw.output.domain.faq.entity.FaqCategory;

public interface FAQRepository extends JpaRepository<Faq, Long> {
    @Query("""
        SELECT f
        FROM Faq f
        WHERE f.isDeleted = false
        AND (:faqCategory = 'ALL' OR f.faqCategory = :faqCategory)
        ORDER BY f.createdAt DESC, f.id DESC
        """)
    Slice<Faq> findFaqFirstPage(Pageable pageable, @Param("faqCategory") FaqCategory faqCategory);

    @Query("""
        SELECT f
        FROM Faq f
        WHERE f.isDeleted = false
        AND (:faqCategory = 'ALL' OR f.faqCategory = :faqCategory)
        AND (f.createdAt < :cursorCreatedAt OR (f.createdAt = :cursorCreatedAt AND f.id < :cursorId))
        ORDER BY f.createdAt DESC, f.id DESC
        """)
    Slice<Faq> findFaqNextPage(Pageable pageable, @Param("cursorId") Long cursorId, @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt, @Param("faqCategory") FaqCategory faqCategory);
}
