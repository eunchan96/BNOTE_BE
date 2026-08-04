package com.bnote.domain.mypage.gratitude.service;

import com.bnote.domain.mypage.gratitude.dto.request.GratitudeNoteRequest;
import com.bnote.domain.mypage.gratitude.dto.response.GratitudeNoteResponse;
import com.bnote.domain.mypage.gratitude.entity.GratitudeEntry;
import com.bnote.domain.mypage.gratitude.entity.GratitudeNote;
import com.bnote.domain.mypage.gratitude.exception.GratitudeException;
import com.bnote.domain.mypage.gratitude.repository.GratitudeEntryRepository;
import com.bnote.domain.mypage.gratitude.repository.GratitudeNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GratitudeService {

	private final GratitudeNoteRepository gratitudeNoteRepository;
	private final GratitudeEntryRepository gratitudeEntryRepository;

	public List<GratitudeNoteResponse> getAll(Long memberId) {
		return gratitudeNoteRepository.findByMemberIdOrderByDateDesc(memberId)
			.stream()
			.map(this::toResponse)
			.toList();
	}

	@Transactional
	public GratitudeNoteResponse save(Long memberId, GratitudeNoteRequest request) {
		GratitudeNote note = gratitudeNoteRepository.findByMemberIdAndDate(memberId, request.date())
			.orElseGet(() -> gratitudeNoteRepository.save(
				GratitudeNote.builder().memberId(memberId).date(request.date()).build()
			));

		gratitudeEntryRepository.deleteByNoteId(note.getId());
		for (int i = 0; i < request.entries().size(); i++) {
			gratitudeEntryRepository.save(
				GratitudeEntry.builder().noteId(note.getId()).text(request.entries().get(i)).sortOrder(i).build()
			);
		}

		return toResponse(note);
	}

	@Transactional
	public void delete(Long memberId, Long id) {
		GratitudeNote note = findOwned(memberId, id);
		gratitudeEntryRepository.deleteByNoteId(note.getId());
		gratitudeNoteRepository.delete(note);
	}

	private GratitudeNote findOwned(Long memberId, Long id) {
		GratitudeNote note = gratitudeNoteRepository.findById(id).orElseThrow(GratitudeException::notFound);
		if (!note.getMemberId().equals(memberId)) {
			throw GratitudeException.accessDenied();
		}
		return note;
	}

	private GratitudeNoteResponse toResponse(GratitudeNote note) {
		List<GratitudeEntry> entries = gratitudeEntryRepository.findByNoteIdOrderBySortOrderAsc(note.getId());
		return GratitudeNoteResponse.of(note, entries);
	}
}