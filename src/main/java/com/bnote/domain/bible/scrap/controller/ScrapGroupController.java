package com.bnote.domain.bible.scrap.controller;

import com.bnote.domain.bible.scrap.dto.request.ScrapGroupRequest;
import com.bnote.domain.bible.scrap.dto.response.ScrapGroupResponse;
import com.bnote.domain.bible.scrap.facade.ScrapFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScrapGroupController implements ScrapGroupControllerDocs {

	private final ScrapFacade scrapFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<ScrapGroupResponse>> getGroups() {
		return RsData.ok("스크랩 그룹 목록 조회 성공", scrapFacade.getGroups(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<ScrapGroupResponse> create(@Valid @RequestBody ScrapGroupRequest request) {
		ScrapGroupResponse response = scrapFacade.createGroup(rq.getActorIdOrThrow(), request);
		return RsData.created("스크랩 그룹 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<ScrapGroupResponse> rename(@PathVariable Long id, @Valid @RequestBody ScrapGroupRequest request) {
		ScrapGroupResponse response = scrapFacade.renameGroup(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("스크랩 그룹 이름 변경 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		scrapFacade.deleteGroup(rq.getActorIdOrThrow(), id);
		return RsData.ok("스크랩 그룹 삭제 성공");
	}
}