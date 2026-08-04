package com.bnote.domain.knowledge.seed;

import com.bnote.domain.knowledge.entity.*;
import com.bnote.domain.knowledge.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * 성경 배경지식 허브 시더. Android 앱 assets/knowledge/ 아래와 동일한 파일명·구조를 그대로 사용한다.
 * 각 파일은 최상위가 배열인 JSON이다(감싸는 객체 없음).
 */
@Slf4j
@Component
public class KnowledgeSeeder implements ApplicationRunner {

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
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String basePath;
	private final boolean seedOnStartup;

	public KnowledgeSeeder(
			BibleFigureRepository bibleFigureRepository,
			BiblePlaceRepository biblePlaceRepository,
			GenealogyChartRepository genealogyChartRepository,
			GenealogyEntryRepository genealogyEntryRepository,
			TimelineEventRepository timelineEventRepository,
			CultureTopicRepository cultureTopicRepository,
			ParableOrMiracleRepository parableOrMiracleRepository,
			TopicalVerseGroupRepository topicalVerseGroupRepository,
			TopicalVerseRefRepository topicalVerseRefRepository,
			BibleUnitRepository bibleUnitRepository,
			@Value("${knowledge.seed.path:knowledge-data/}") String basePath,
			@Value("${knowledge.seed.on-startup:true}") boolean seedOnStartup
	) {
		this.bibleFigureRepository = bibleFigureRepository;
		this.biblePlaceRepository = biblePlaceRepository;
		this.genealogyChartRepository = genealogyChartRepository;
		this.genealogyEntryRepository = genealogyEntryRepository;
		this.timelineEventRepository = timelineEventRepository;
		this.cultureTopicRepository = cultureTopicRepository;
		this.parableOrMiracleRepository = parableOrMiracleRepository;
		this.topicalVerseGroupRepository = topicalVerseGroupRepository;
		this.topicalVerseRefRepository = topicalVerseRefRepository;
		this.bibleUnitRepository = bibleUnitRepository;
		this.basePath = basePath;
		this.seedOnStartup = seedOnStartup;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!seedOnStartup) {
			log.info("[KnowledgeSeeder] knowledge.seed.on-startup=false 라서 자동 시딩을 건너뜁니다.");
			return;
		}
		seedIfEmpty();
	}

	@Transactional
	public void seedIfEmpty() {
		if (bibleFigureRepository.count() == 0) {
			seedFigures(readArray("bible_figures.json"));
		}
		if (biblePlaceRepository.count() == 0) {
			seedPlaces(readArray("bible_places.json"));
		}
		if (genealogyChartRepository.count() == 0) {
			seedGenealogies(readArray("genealogy_charts.json"));
		}
		if (timelineEventRepository.count() == 0) {
			seedTimeline(readArray("bible_timeline.json"));
		}
		if (cultureTopicRepository.count() == 0) {
			seedCultureTopics(readArray("bible_culture.json"));
		}
		if (parableOrMiracleRepository.count() == 0) {
			seedParables(readArray("parables_miracles.json"));
		}
		if (topicalVerseGroupRepository.count() == 0) {
			seedTopicalVerses(readArray("topical_verses.json"));
		}
		if (bibleUnitRepository.count() == 0) {
			seedUnits(readArray("bible_unit.json"));
		}
	}

	private JsonNode readArray(String fileName) {
		Resource resource = new ClassPathResource(basePath + fileName);
		if (!resource.exists()) {
			log.warn("[KnowledgeSeeder] 시드 파일이 없어 건너뜁니다: {}{}", basePath, fileName);
			return null;
		}
		try (InputStream in = resource.getInputStream()) {
			return objectMapper.readTree(in);
		} catch (IOException e) {
			log.error("[KnowledgeSeeder] {} 파일을 읽는 중 오류가 발생했습니다.", fileName, e);
			return null;
		}
	}

	private void seedFigures(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			bibleFigureRepository.save(
					BibleFigure.builder()
							.id(text(o, "id")).name(text(o, "name")).otherNames(text(o, "otherNames"))
							.category(text(o, "category")).era(text(o, "era")).summary(text(o, "summary"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 인물사전 {}개 시딩 완료", array.size());
	}

	private void seedPlaces(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			biblePlaceRepository.save(
					BiblePlace.builder()
							.id(text(o, "id")).name(text(o, "name")).otherNames(text(o, "otherNames"))
							.category(text(o, "category")).region(text(o, "region")).summary(text(o, "summary"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 지명사전 {}개 시딩 완료", array.size());
	}

	private void seedGenealogies(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			GenealogyChart chart = genealogyChartRepository.save(
					GenealogyChart.builder()
							.id(text(o, "id")).title(text(o, "title")).description(text(o, "description"))
							.keyBookId(intOrNull(o, "keyBookId")).keyChapter(intOrNull(o, "keyChapter"))
							.keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
			JsonNode entries = o.get("entries");
			if (entries != null) {
				int i = 0;
				for (JsonNode e : entries) {
					genealogyEntryRepository.save(
							GenealogyEntry.builder()
									.chartId(chart.getId()).name(text(e, "name")).relation(text(e, "relation"))
									.note(text(e, "note")).sortOrder(i++)
									.build()
					);
				}
			}
		}
		log.info("[KnowledgeSeeder] 족보 {}개 시딩 완료", array.size());
	}

	private void seedTimeline(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			timelineEventRepository.save(
					TimelineEvent.builder()
							.id(text(o, "id")).era(text(o, "era")).period(text(o, "period")).title(text(o, "title"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 연대표 {}개 시딩 완료", array.size());
	}

	private void seedCultureTopics(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			cultureTopicRepository.save(
					CultureTopic.builder()
							.id(text(o, "id")).title(text(o, "title")).category(text(o, "category")).summary(text(o, "summary"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 당시 문화 {}개 시딩 완료", array.size());
	}

	private void seedParables(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			parableOrMiracleRepository.save(
					ParableOrMiracle.builder()
							.id(text(o, "id")).title(text(o, "title")).type(text(o, "type")).summary(text(o, "summary"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 비유와 이적 {}개 시딩 완료", array.size());
	}

	private void seedTopicalVerses(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			TopicalVerseGroup group = topicalVerseGroupRepository.save(
					TopicalVerseGroup.builder().id(text(o, "id")).title(text(o, "title")).build()
			);
			JsonNode verses = o.get("verses");
			if (verses != null) {
				for (JsonNode v : verses) {
					topicalVerseRefRepository.save(
							TopicalVerseRef.builder()
									.groupId(group.getId()).bookId(intOrNull(v, "bookId")).chapter(intOrNull(v, "chapter"))
									.verseStart(intOrNull(v, "verseStart")).verseEnd(intOrNull(v, "verseEnd"))
									.build()
					);
				}
			}
		}
		log.info("[KnowledgeSeeder] 상황별 말씀 {}개 그룹 시딩 완료", array.size());
	}

	private void seedUnits(JsonNode array) {
		if (array == null) {
			return;
		}
		for (JsonNode o : array) {
			bibleUnitRepository.save(
					BibleUnit.builder()
							.id(text(o, "id")).title(text(o, "title")).category(text(o, "category")).summary(text(o, "summary"))
							.description(text(o, "description")).keyBookId(intOrNull(o, "keyBookId"))
							.keyChapter(intOrNull(o, "keyChapter")).keyVerseLabel(text(o, "keyVerseLabel"))
							.build()
			);
		}
		log.info("[KnowledgeSeeder] 성경 단위(도량형) {}개 시딩 완료", array.size());
	}

	private String text(JsonNode obj, String field) {
		JsonNode node = obj.get(field);
		return (node == null || node.isNull()) ? null : node.asString();
	}

	private Integer intOrNull(JsonNode obj, String field) {
		JsonNode node = obj.get(field);
		return (node == null || node.isNull()) ? null : node.asInt();
	}
}