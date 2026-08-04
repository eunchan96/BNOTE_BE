package com.bnote.domain.application.repository;

import com.bnote.domain.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByMemberIdOrderByApplicationDateDesc(Long memberId);

	List<Application> findByMemberIdAndTitleContainingOrderByApplicationDateDesc(Long memberId, String keyword);
}