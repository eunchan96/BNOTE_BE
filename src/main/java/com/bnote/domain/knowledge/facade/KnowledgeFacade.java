package com.bnote.domain.knowledge.facade;

import com.bnote.domain.knowledge.dto.response.*;
import com.bnote.domain.knowledge.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KnowledgeFacade {

	private final KnowledgeService knowledgeService;

	public List<BibleFigureResponse> getFigures(String keyword) {
		return knowledgeService.getFigures(keyword);
	}

	public List<BiblePlaceResponse> getPlaces(String keyword) {
		return knowledgeService.getPlaces(keyword);
	}

	public List<GenealogyChartResponse> getGenealogies() {
		return knowledgeService.getGenealogies();
	}

	public List<TimelineEventResponse> getTimeline() {
		return knowledgeService.getTimeline();
	}

	public List<CultureTopicResponse> getCultureTopics() {
		return knowledgeService.getCultureTopics();
	}

	public List<ParableOrMiracleResponse> getParables() {
		return knowledgeService.getParables();
	}

	public List<TopicalVerseGroupResponse> getTopicalVerses() {
		return knowledgeService.getTopicalVerses();
	}

	public List<BibleUnitResponse> getUnits() {
		return knowledgeService.getUnits();
	}
}