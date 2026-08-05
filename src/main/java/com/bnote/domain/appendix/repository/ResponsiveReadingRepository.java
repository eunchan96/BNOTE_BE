package com.bnote.domain.appendix.repository;

import com.bnote.domain.appendix.entity.ResponsiveReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsiveReadingRepository extends JpaRepository<ResponsiveReadingEntity, Integer> {

	List<ResponsiveReadingEntity> findAllByOrderByNumberAsc();
}