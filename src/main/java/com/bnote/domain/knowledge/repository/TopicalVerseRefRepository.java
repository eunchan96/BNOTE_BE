package com.bnote.domain.knowledge.repository;

import com.bnote.domain.knowledge.entity.TopicalVerseRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicalVerseRefRepository extends JpaRepository<TopicalVerseRef, Long> {

	List<TopicalVerseRef> findByGroupId(String groupId);
}