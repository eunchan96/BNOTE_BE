package com.bnote.domain.bible.bookmark.repository;

import com.bnote.domain.bible.bookmark.entity.BibleBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BibleBookmarkRepository extends JpaRepository<BibleBookmark, Long> {

	@Query("""
		SELECT b FROM BibleBookmark b
		WHERE b.memberId = :memberId AND b.bookId = :bookId AND b.chapter = :chapter
		AND (b.isBookmarked = true OR b.isHighlighted = true)
		ORDER BY b.verse ASC
		""")
	List<BibleBookmark> findActiveByMemberAndChapter(
			@Param("memberId") Long memberId, @Param("bookId") Integer bookId, @Param("chapter") Integer chapter
	);

	Optional<BibleBookmark> findByMemberIdAndBookIdAndChapterAndVerse(
			Long memberId, Integer bookId, Integer chapter, Integer verse
	);
}