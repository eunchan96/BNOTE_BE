package com.bnote.domain.bible.scrap.controller;

import com.bnote.domain.bible.scrap.dto.request.ScrapRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapResponse;
import com.bnote.domain.bible.scrap.facade.ScrapFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapController implements ScrapControllerDocs {

	private final ScrapFacade scrapFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<ScrapResponse>> getByGroup(@RequestParam Long groupId) {
		return RsData.ok("스크랩 목록 조회 성공", scrapFacade.getScraps(rq.getActorIdOrThrow(), groupId));
	}

	@PostMapping
	public RsData<ScrapResponse> create(@Valid @RequestBody ScrapRequest request) {
		ScrapResponse response = scrapFacade.createScrap(rq.getActorIdOrThrow(), request);
		return RsData.created("스크랩 등록 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		scrapFacade.deleteScrap(rq.getActorIdOrThrow(), id);
		return RsData.ok("스크랩 삭제 성공");
	}
}