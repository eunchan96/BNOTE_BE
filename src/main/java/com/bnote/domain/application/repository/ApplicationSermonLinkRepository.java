package com.bnote.domain.application.repository;

import com.bnote.domain.application.entity.ApplicationSermonLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationSermonLinkRepository extends JpaRepository<ApplicationSermonLink, Long> {

	List<ApplicationSermonLink> findByApplicationId(Long applicationId);

	void deleteByApplicationId(Long applicationId);
}