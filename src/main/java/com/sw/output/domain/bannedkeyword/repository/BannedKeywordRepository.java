package com.sw.output.domain.bannedkeyword.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sw.output.domain.bannedkeyword.entity.BannedKeyword;

public interface BannedKeywordRepository extends JpaRepository<BannedKeyword, Long> {

}
