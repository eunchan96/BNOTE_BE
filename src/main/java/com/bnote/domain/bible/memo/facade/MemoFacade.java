package com.bnote.domain.bible.memo.facade;

import com.bnote.domain.bible.memo.dto.request.VerseMemoRequest;
import com.bnote.domain.bible.memo.dto.request.VerseMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.request.WordMemoRequest;
import com.bnote.domain.bible.memo.dto.request.WordMemoUpdateRequest;
import com.bnote.domain.bible.memo.dto.response.VerseMemoResponse;
import com.bnote.domain.bible.memo.dto.response.WordMemoResponse;
import com.bnote.domain.bible.memo.service.VerseMemoService;
import com.bnote.domain.bible.memo.service.WordMemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemoFacade {

	private final VerseMemoService verseMemoService;
	private final WordMemoService wordMemoService;

	public List<VerseMemoResponse> getVerseMemos(Long memberId, Integer bookId, Integer chapter) {
		return verseMemoService.getByChapter(memberId, bookId, chapter);
	}

	public VerseMemoResponse createVerseMemo(Long memberId, VerseMemoRequest request) {
		return verseMemoService.create(memberId, request);
	}

	public VerseMemoResponse updateVerseMemo(Long memberId, Long id, VerseMemoUpdateRequest request) {
		return verseMemoService.update(memberId, id, request);
	}

	public void deleteVerseMemo(Long memberId, Long id) {
		verseMemoService.delete(memberId, id);
	}

	public List<WordMemoResponse> getWordMemos(Long memberId, Integer bookId, Integer chapter) {
		return wordMemoService.getByChapter(memberId, bookId, chapter);
	}

	public WordMemoResponse createWordMemo(Long memberId, WordMemoRequest request) {
		return wordMemoService.create(memberId, request);
	}

	public WordMemoResponse updateWordMemo(Long memberId, Long id, WordMemoUpdateRequest request) {
		return wordMemoService.update(memberId, id, request);
	}

	public void deleteWordMemo(Long memberId, Long id) {
		wordMemoService.delete(memberId, id);
	}
}