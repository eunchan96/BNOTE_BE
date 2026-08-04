package com.bnote.domain.mypage.verseofyear.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.entity.Translation;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRefRequest;
import com.bnote.domain.mypage.verseofyear.dto.request.VerseOfYearRequest;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearRefResponse;
import com.bnote.domain.mypage.verseofyear.dto.response.VerseOfYearResponse;
import com.bnote.domain.mypage.verseofyear.entity.VerseOfYear;
import com.bnote.domain.mypage.verseofyear.entity.VerseOfYearRef;
import com.bnote.domain.mypage.verseofyear.exception.VerseOfYearException;
import com.bnote.domain.mypage.verseofyear.repository.VerseOfYearRefRepository;
import com.bnote.domain.mypage.verseofyear.repository.VerseOfYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerseOfYearService {

	private final VerseOfYearRepository verseOfYearRepository;
	private final VerseOfYearRefRepository verseOfYearRefRepository;
	private final BibleVerseRepository bibleVerseRepository;

	public VerseOfYearResponse getByYear(Long memberId, Integer year) {
		VerseOfYear verseOfYear = verseOfYearRepository.findByMemberIdAndYear(memberId, year)
			.orElseThrow(VerseOfYearException::notFound);

		return toResponse(verseOfYear);
	}

	@Transactional
	public VerseOfYearResponse save(Long memberId, VerseOfYearRequest request) {
		VerseOfYear verseOfYear = verseOfYearRepository.findByMemberIdAndYear(memberId, request.year())
			.map(existing -> {
				existing.updateNote(request.note());
				return existing;
			})
			.orElseGet(() -> verseOfYearRepository.save(
				VerseOfYear.builder().memberId(memberId).year(request.year()).note(request.note()).build()
			));

		verseOfYearRefRepository.deleteByVerseOfYearId(verseOfYear.getId());
		saveRefs(verseOfYear.getId(), request.verseRefs());

		return toResponse(verseOfYear);
	}

	private void saveRefs(Long verseOfYearId, List<VerseOfYearRefRequest> refs) {
		if (refs == null) {
			return;
		}
		for (VerseOfYearRefRequest ref : refs) {
			String translation = (ref.translation() == null || ref.translation().isBlank())
				? Translation.NKRV.getCode()
				: ref.translation();

			String verseText = buildVerseText(translation, ref);

			verseOfYearRefRepository.save(
				VerseOfYearRef.builder()
					.verseOfYearId(verseOfYearId)
					.startBookId(ref.startBookId())
					.startChapter(ref.startChapter())
					.startVerse(ref.startVerse())
					.endBookId(ref.endBookId())
					.endChapter(ref.endChapter())
					.endVerse(ref.endVerse())
					.verseText(verseText)
					.build()
			);
		}
	}

	private String buildVerseText(String translation, VerseOfYearRefRequest ref) {
		// 같은 장 안의 범위만 지원(대부분의 "올해의 말씀"은 한 장 안에서 몇 절)
		List<BibleVerse> verses = bibleVerseRepository.findByTranslationAndBookIdAndChapterAndVerseBetweenOrderByVerseAsc(
			translation, ref.startBookId(), ref.startChapter(), ref.startVerse(), ref.endVerse()
		);
		return verses.stream().map(BibleVerse::getText).reduce((a, b) -> a + " " + b).orElse("");
	}

	private VerseOfYearResponse toResponse(VerseOfYear verseOfYear) {
		List<VerseOfYearRefResponse> refs = verseOfYearRefRepository.findByVerseOfYearId(verseOfYear.getId())
			.stream().map(VerseOfYearRefResponse::from).toList();
		return VerseOfYearResponse.of(verseOfYear, refs);
	}
}