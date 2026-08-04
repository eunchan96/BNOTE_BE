package com.bnote.domain.knowledge.service;

import com.bnote.domain.knowledge.dto.response.*;
import com.bnote.domain.knowledge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KnowledgeService {

	private final BibleFigureRepository bibleFigureRepository;
	private final BiblePlaceRepository biblePlaceRepository;
	private final GenealogyChartRepository genealogyChartRepository;
	private final GenealogyEntryRepository genealogyEntryRepository;
	private final TimelineEventRepository timelineEventRepository;
	private final CultureTopicRepository cultureTopicRepository;
	private final ParableOrMiracleRepository parableOrMiracleRepository;
	private final TopicalVerseGroupRepository topicalVerseGroupRepository;
	private final TopicalVerseRefRepository topicalVerseRefRepository;
	private final BibleUnitRepository bibleUnitRepository;

	public List<BibleFigureResponse> getFigures(String keyword) {
		var figures = (keyword == null || keyword.isBlank())
			? bibleFigureRepository.findAll()
			: bibleFigureRepository.findByNameContainingOrOtherNamesContaining(keyword.trim(), keyword.trim());
		return figures.stream().map(BibleFigureResponse::from).toList();
	}

	public List<BiblePlaceResponse> getPlaces(String keyword) {
		var places = (keyword == null || keyword.isBlank())
			? biblePlaceRepository.findAll()
			: biblePlaceRepository.findByNameContainingOrOtherNamesContaining(keyword.trim(), keyword.trim());
		return places.stream().map(BiblePlaceResponse::from).toList();
	}

	public List<GenealogyChartResponse> getGenealogies() {
		return genealogyChartRepository.findAll().stream()
			.map(chart -> GenealogyChartResponse.of(chart, genealogyEntryRepository.findByChartIdOrderBySortOrderAsc(chart.getId())))
			.toList();
	}

	public List<TimelineEventResponse> getTimeline() {
		return timelineEventRepository.findAll().stream().map(TimelineEventResponse::from).toList();
	}

	public List<CultureTopicResponse> getCultureTopics() {
		return cultureTopicRepository.findAll().stream().map(CultureTopicResponse::from).toList();
	}

	public List<ParableOrMiracleResponse> getParables() {
		return parableOrMiracleRepository.findAll().stream().map(ParableOrMiracleResponse::from).toList();
	}

	public List<TopicalVerseGroupResponse> getTopicalVerses() {
		return topicalVerseGroupRepository.findAll().stream()
			.map(group -> TopicalVerseGroupResponse.of(group, topicalVerseRefRepository.findByGroupId(group.getId())))
			.toList();
	}

	public List<BibleUnitResponse> getUnits() {
		return bibleUnitRepository.findAll().stream().map(BibleUnitResponse::from).toList();
	}
}