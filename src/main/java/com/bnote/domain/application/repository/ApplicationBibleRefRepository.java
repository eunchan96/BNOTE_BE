package com.bnote.domain.application.repository;

import com.bnote.domain.application.entity.ApplicationBibleRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationBibleRefRepository extends JpaRepository<ApplicationBibleRef, Long> {

	List<ApplicationBibleRef> findByApplicationId(Long applicationId);

	void deleteByApplicationId(Long applicationId);
}