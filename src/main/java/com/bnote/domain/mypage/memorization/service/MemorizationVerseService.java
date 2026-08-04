package com.bnote.domain.mypage.memorization.service;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.entity.Translation;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationReviewRequest;
import com.bnote.domain.mypage.memorization.dto.request.MemorizationVerseRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationVerseResponse;
import com.bnote.domain.mypage.memorization.entity.MemorizationGroup;
import com.bnote.domain.mypage.memorization.entity.MemorizationVerse;
import com.bnote.domain.mypage.memorization.exception.MemorizationException;
import com.bnote.domain.mypage.memorization.repository.MemorizationVerseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorizationVerseService {

	private final MemorizationVerseRepository memorizationVerseRepository;
	private final MemorizationGroupService memorizationGroupService;
	private final BibleVerseRepository bibleVerseRepository;

	public List<MemorizationVerseResponse> getByGroup(Long memberId, Long groupId) {
		memorizationGroupService.findOwned(memberId, groupId);
		return memorizationVerseRepository.findByGroupIdOrderByCreateDateDesc(groupId)
			.stream()
			.map(MemorizationVerseResponse::from)
			.toList();
	}

	@Transactional
	public MemorizationVerseResponse create(Long memberId, MemorizationVerseRequest request) {
		MemorizationGroup group = memorizationGroupService.findOwned(memberId, request.groupId());

		String translation = (request.translation() == null || request.translation().isBlank())
			? Translation.NKRV.getCode()
			: request.translation();

		String verseText = buildVerseText(translation, request);

		MemorizationVerse verse = MemorizationVerse.builder()
			.groupId(group.getId())
			.startBookId(request.startBookId())
			.startChapter(request.startChapter())
			.startVerse(request.startVerse())
			.endBookId(request.endBookId())
			.endChapter(request.endChapter())
			.endVerse(request.endVerse())
			.verseText(verseText)
			.note(request.note())
			.build();

		return MemorizationVerseResponse.from(memorizationVerseRepository.save(verse));
	}

	@Transactional
	public MemorizationVerseResponse review(Long memberId, Long id, MemorizationReviewRequest request) {
		MemorizationVerse verse = findOwnedVerse(memberId, id);
		verse.recordReview(request.mastered());
		return MemorizationVerseResponse.from(verse);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		memorizationVerseRepository.delete(findOwnedVerse(memberId, id));
	}

	private MemorizationVerse findOwnedVerse(Long memberId, Long id) {
		MemorizationVerse verse = memorizationVerseRepository.findById(id)
			.orElseThrow(MemorizationException::verseNotFound);
		memorizationGroupService.findOwned(memberId, verse.getGroupId());
		return verse;
	}

	private String buildVerseText(String translation, MemorizationVerseRequest request) {
		if (!request.startChapter().equals(request.endChapter()) || !request.startBookId().equals(request.endBookId())) {
			return ""; // 장을 넘어가는 범위는 스냅샷 생략(프론트에서 직접 채우도록)
		}
		List<BibleVerse> verses = bibleVerseRepository.findByTranslationAndBookIdAndChapterAndVerseBetweenOrderByVerseAsc(
			translation, request.startBookId(), request.startChapter(), request.startVerse(), request.endVerse()
		);
		return verses.stream().map(BibleVerse::getText).reduce((a, b) -> a + " " + b).orElse("");
	}
}