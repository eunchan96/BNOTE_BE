package com.bnote.domain.bible.scrap.repository;

import com.bnote.domain.bible.scrap.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {

	List<Scrap> findByGroupIdOrderByCreateDateDesc(Long groupId);

	long countByGroupId(Long groupId);
}