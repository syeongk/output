package com.sw.output.domain.faq.repository;

import com.sw.output.domain.faq.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<Faq, Long> {
}
