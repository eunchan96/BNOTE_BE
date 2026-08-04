package com.bnote.domain.bible.scrap.repository;

import com.bnote.domain.bible.scrap.entity.ScrapGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapGroupRepository extends JpaRepository<ScrapGroup, Long> {

	List<ScrapGroup> findByMemberIdOrderBySortOrderAsc(Long memberId);
}