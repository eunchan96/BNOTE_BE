package com.bnote.domain.mypage.recentactivity.repository;

import com.bnote.domain.mypage.recentactivity.entity.RecentChapterView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecentChapterViewRepository extends JpaRepository<RecentChapterView, Long> {

	java.util.List<RecentChapterView> findByMemberIdOrderByViewedAtDesc(Long memberId, Pageable pageable);

	Optional<RecentChapterView> findByMemberIdAndBookIdAndChapter(Long memberId, Integer bookId, Integer chapter);
}