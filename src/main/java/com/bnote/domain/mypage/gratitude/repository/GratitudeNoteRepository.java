package com.bnote.domain.mypage.gratitude.repository;

import com.bnote.domain.mypage.gratitude.entity.GratitudeNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GratitudeNoteRepository extends JpaRepository<GratitudeNote, Long> {

	List<GratitudeNote> findByMemberIdOrderByDateDesc(Long memberId);

	Optional<GratitudeNote> findByMemberIdAndDate(Long memberId, LocalDate date);
}