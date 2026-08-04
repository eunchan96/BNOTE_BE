package com.bnote.domain.sermon.repository;

import com.bnote.domain.sermon.entity.Preacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreacherRepository extends JpaRepository<Preacher, Long> {

	List<Preacher> findByMemberIdOrderBySortOrderAsc(Long memberId);
}