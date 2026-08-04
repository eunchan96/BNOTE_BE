package com.bnote.domain.mypage.readingprogress.service;

import com.bnote.domain.bible.bible.entity.BibleBooks;
import com.bnote.domain.bible.bible.exception.BibleException;
import com.bnote.domain.mypage.readingprogress.dto.request.ReadingProgressRequest;
import com.bnote.domain.mypage.readingprogress.dto.response.ReadingProgressResponse;
import com.bnote.domain.mypage.readingprogress.entity.ReadingProgress;
import com.bnote.domain.mypage.readingprogress.exception.ReadingProgressException;
import com.bnote.domain.mypage.readingprogress.repository.ReadingProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReadingProgressService {

	private final ReadingProgressRepository readingProgressRepository;

	public List<ReadingProgressResponse> getAll(Long memberId) {
		return readingProgressRepository.findByMemberIdOrderByBookIdAscChapterAsc(memberId)
			.stream()
			.map(ReadingProgressResponse::from)
			.toList();
	}

	@Transactional
	public ReadingProgressResponse check(Long memberId, ReadingProgressRequest request) {
		validateBookId(request.bookId());

		ReadingProgress progress = readingProgressRepository
			.findByMemberIdAndBookIdAndChapter(memberId, request.bookId(), request.chapter())
			.orElseGet(() -> readingProgressRepository.save(
				ReadingProgress.builder()
					.memberId(memberId)
					.bookId(request.bookId())
					.chapter(request.chapter())
					.readDate(LocalDate.now())
					.build()
			));

		return ReadingProgressResponse.from(progress);
	}

	@Transactional
	public void cancel(Long memberId, Long id) {
		ReadingProgress progress = readingProgressRepository.findById(id)
			.orElseThrow(ReadingProgressException::notFound);

		if (!progress.getMemberId().equals(memberId)) {
			throw ReadingProgressException.accessDenied();
		}
		readingProgressRepository.delete(progress);
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw BibleException.invalidBookId();
		}
	}
}