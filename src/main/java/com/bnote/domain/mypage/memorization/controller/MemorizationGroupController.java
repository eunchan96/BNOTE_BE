package com.bnote.domain.mypage.memorization.controller;

import com.bnote.domain.mypage.memorization.dto.request.MemorizationGroupRequest;
import com.bnote.domain.mypage.memorization.dto.response.MemorizationGroupResponse;
import com.bnote.domain.mypage.memorization.facade.MemorizationFacade;
import com.bnote.global.response.RsData;
import com.bnote.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemorizationGroupController implements MemorizationGroupControllerDocs {

	private final MemorizationFacade memorizationFacade;
	private final Rq rq;

	@GetMapping
	public RsData<List<MemorizationGroupResponse>> getAll() {
		return RsData.ok("암송 그룹 목록 조회 성공", memorizationFacade.getGroups(rq.getActorIdOrThrow()));
	}

	@PostMapping
	public RsData<MemorizationGroupResponse> create(@Valid @RequestBody MemorizationGroupRequest request) {
		MemorizationGroupResponse response = memorizationFacade.createGroup(rq.getActorIdOrThrow(), request);
		return RsData.created("암송 그룹 등록 성공", response);
	}

	@PutMapping("/{id}")
	public RsData<MemorizationGroupResponse> update(@PathVariable Long id, @Valid @RequestBody MemorizationGroupRequest request) {
		MemorizationGroupResponse response = memorizationFacade.renameGroup(rq.getActorIdOrThrow(), id, request);
		return RsData.ok("암송 그룹 이름 변경 성공", response);
	}

	@DeleteMapping("/{id}")
	public RsData<Void> delete(@PathVariable Long id) {
		memorizationFacade.deleteGroup(rq.getActorIdOrThrow(), id);
		return RsData.ok("암송 그룹 삭제 성공");
	}
}