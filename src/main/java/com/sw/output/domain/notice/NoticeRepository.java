package com.sw.output.domain.notice;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

}
