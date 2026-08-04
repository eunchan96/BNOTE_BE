package com.bnote.domain.application.repository;

import com.bnote.domain.application.entity.ApplicationCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationCategoryRepository extends JpaRepository<ApplicationCategory, Long> {

	List<ApplicationCategory> findByMemberIdOrderBySortOrderAsc(Long memberId);

	boolean existsByMemberId(Long memberId);
}