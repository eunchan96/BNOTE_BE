package com.bnote.domain.mypage.verseofyear.repository;

import com.bnote.domain.mypage.verseofyear.entity.VerseOfYear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerseOfYearRepository extends JpaRepository<VerseOfYear, Long> {

	Optional<VerseOfYear> findByMemberIdAndYear(Long memberId, Integer year);
}