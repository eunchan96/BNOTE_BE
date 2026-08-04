package com.bnote.domain.knowledge.controller;

import com.bnote.domain.knowledge.dto.response.*;
import com.bnote.domain.knowledge.facade.KnowledgeFacade;
import com.bnote.global.response.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class KnowledgeController implements KnowledgeControllerDocs {

	private final KnowledgeFacade knowledgeFacade;

	@GetMapping("api/v1/knowledge/figures")
	public RsData<List<BibleFigureResponse>> getFigures(@RequestParam(required = false) String keyword) {
		return RsData.ok("인물사전 조회 성공", knowledgeFacade.getFigures(keyword));
	}

	@GetMapping("api/v1/knowledge/places")
	public RsData<List<BiblePlaceResponse>> getPlaces(@RequestParam(required = false) String keyword) {
		return RsData.ok("지명사전 조회 성공", knowledgeFacade.getPlaces(keyword));
	}

	@GetMapping("api/v1/knowledge/genealogies")
	public RsData<List<GenealogyChartResponse>> getGenealogies() {
		return RsData.ok("족보 조회 성공", knowledgeFacade.getGenealogies());
	}

	@GetMapping("api/v1/knowledge/timeline")
	public RsData<List<TimelineEventResponse>> getTimeline() {
		return RsData.ok("연대표 조회 성공", knowledgeFacade.getTimeline());
	}

	@GetMapping("api/v1/knowledge/culture")
	public RsData<List<CultureTopicResponse>> getCultureTopics() {
		return RsData.ok("당시 문화 조회 성공", knowledgeFacade.getCultureTopics());
	}

	@GetMapping("api/v1/knowledge/parables")
	public RsData<List<ParableOrMiracleResponse>> getParables() {
		return RsData.ok("비유와 이적 조회 성공", knowledgeFacade.getParables());
	}

	@GetMapping("api/v1/knowledge/topical-verses")
	public RsData<List<TopicalVerseGroupResponse>> getTopicalVerses() {
		return RsData.ok("상황별 말씀 조회 성공", knowledgeFacade.getTopicalVerses());
	}

	@GetMapping("api/v1/knowledge/units")
	public RsData<List<BibleUnitResponse>> getUnits() {
		return RsData.ok("성경 단위 조회 성공", knowledgeFacade.getUnits());
	}
}