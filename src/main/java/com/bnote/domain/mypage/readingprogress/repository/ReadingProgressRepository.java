package com.bnote.domain.mypage.readingprogress.repository;

import com.bnote.domain.mypage.readingprogress.entity.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {

	List<ReadingProgress> findByMemberIdOrderByBookIdAscChapterAsc(Long memberId);

	Optional<ReadingProgress> findByMemberIdAndBookIdAndChapter(Long memberId, Integer bookId, Integer chapter);
}