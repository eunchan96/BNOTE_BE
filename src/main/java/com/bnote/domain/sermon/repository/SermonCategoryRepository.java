package com.bnote.domain.sermon.repository;

import com.bnote.domain.sermon.entity.SermonCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermonCategoryRepository extends JpaRepository<SermonCategory, Long> {

	List<SermonCategory> findByMemberIdOrderBySortOrderAsc(Long memberId);

	boolean existsByMemberId(Long memberId);
}