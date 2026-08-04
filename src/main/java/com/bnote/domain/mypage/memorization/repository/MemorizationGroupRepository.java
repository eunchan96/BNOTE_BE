package com.bnote.domain.mypage.memorization.repository;

import com.bnote.domain.mypage.memorization.entity.MemorizationGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemorizationGroupRepository extends JpaRepository<MemorizationGroup, Long> {

	List<MemorizationGroup> findByMemberIdOrderBySortOrderAsc(Long memberId);
}