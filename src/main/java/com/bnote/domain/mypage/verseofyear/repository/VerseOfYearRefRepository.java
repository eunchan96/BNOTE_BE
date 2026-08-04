package com.bnote.domain.mypage.verseofyear.repository;

import com.bnote.domain.mypage.verseofyear.entity.VerseOfYearRef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerseOfYearRefRepository extends JpaRepository<VerseOfYearRef, Long> {

	List<VerseOfYearRef> findByVerseOfYearId(Long verseOfYearId);

	void deleteByVerseOfYearId(Long verseOfYearId);
}