package com.bnote.domain.partialhighlight.service;

import com.bnote.domain.bible.entity.BibleBooks;
import com.bnote.domain.bible.exception.BibleException;
import com.bnote.domain.partialhighlight.dto.request.PartialHighlightRequest;
import com.bnote.domain.partialhighlight.dto.response.PartialHighlightResponse;
import com.bnote.domain.partialhighlight.entity.PartialHighlight;
import com.bnote.domain.partialhighlight.exception.PartialHighlightException;
import com.bnote.domain.partialhighlight.repository.PartialHighlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartialHighlightService {

	private final PartialHighlightRepository partialHighlightRepository;

	public List<PartialHighlightResponse> getByLocation(Long memberId, Integer bookId, Integer chapter, Integer verse) {
		validateBookId(bookId);

		List<PartialHighlight> highlights = (verse == null)
			? partialHighlightRepository.findByMemberIdAndBookIdAndChapterOrderByVerseAsc(memberId, bookId, chapter)
			: partialHighlightRepository.findByMemberIdAndBookIdAndChapterAndVerseOrderByStartOffsetAsc(
				memberId, bookId, chapter, verse
			);

		return highlights.stream().map(PartialHighlightResponse::from).toList();
	}

	@Transactional
	public PartialHighlightResponse create(Long memberId, PartialHighlightRequest request) {
		validateBookId(request.bookId());

		PartialHighlight highlight = PartialHighlight.builder()
			.memberId(memberId)
			.translation(request.translation())
			.bookId(request.bookId())
			.chapter(request.chapter())
			.verse(request.verse())
			.startOffset(request.startOffset())
			.endOffset(request.endOffset())
			.segment(request.segment())
			.colorHex(request.colorHex())
			.build();

		return PartialHighlightResponse.from(partialHighlightRepository.save(highlight));
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		PartialHighlight highlight = partialHighlightRepository.findById(id)
			.orElseThrow(PartialHighlightException::notFound);

		if (!highlight.getMemberId().equals(memberId)) {
			throw PartialHighlightException.accessDenied();
		}

		partialHighlightRepository.delete(highlight);
	}

	private void validateBookId(Integer bookId) {
		if (bookId == null || bookId < 1 || bookId > BibleBooks.totalBookCount()) {
			throw BibleException.invalidBookId();
		}
	}
}