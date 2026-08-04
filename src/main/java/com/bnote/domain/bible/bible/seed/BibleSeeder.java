package com.bnote.domain.bible.bible.seed;

import com.bnote.domain.bible.bible.entity.BibleVerse;
import com.bnote.domain.bible.bible.entity.Translation;
import com.bnote.domain.bible.bible.repository.BibleVerseRepository;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 앱 시작 시 각 번역본이 비어있으면 src/main/resources/bible-data/{assetFileName}을 읽어 채운다.
 * 이미 데이터가 있는 번역본은 건드리지 않는다(운영 DB에서 매번 다시 긁지 않도록).
 *
 * 파일이 없는 번역본은 조용히 건너뛴다 — 아직 전체 번역본 JSON을 다 옮기지 못했어도
 * 서버가 정상 기동되도록 하기 위함이다. 어떤 번역본이 비었는지는 로그로 남긴다.
 */
@Slf4j
@Component
public class BibleSeeder implements ApplicationRunner {

	private final BibleVerseRepository bibleVerseRepository;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String basePath;
	private final boolean seedOnStartup;

	public BibleSeeder(
			BibleVerseRepository bibleVerseRepository,
			@Value("${bible.seed.path:bible-data/}") String basePath,
			@Value("${bible.seed.on-startup:true}") boolean seedOnStartup
	) {
		this.bibleVerseRepository = bibleVerseRepository;
		this.basePath = basePath;
		this.seedOnStartup = seedOnStartup;
	}

	@Override
	public void run(ApplicationArguments args) {
		if (!seedOnStartup) {
			log.info("[BibleSeeder] bible.seed.on-startup=false 라서 자동 시딩을 건너뜁니다.");
			return;
		}
		for (Translation translation : Translation.values()) {
			seedIfEmpty(translation);
		}
	}

	@Transactional
	void seedIfEmpty(Translation translation) {
		if (bibleVerseRepository.countByTranslation(translation.getCode()) > 0) {
			return;
		}

		Resource resource = new ClassPathResource(basePath + translation.getAssetFileName());
		if (!resource.exists()) {
			log.warn("[BibleSeeder] {} 시드 파일이 없어 건너뜁니다: {}{}", translation.getCode(), basePath, translation.getAssetFileName());
			return;
		}

		JsonNode root;
		try (InputStream in = resource.getInputStream()) {
			root = objectMapper.readTree(in);
		} catch (IOException e) {
			log.error("[BibleSeeder] {} 시드 파일을 읽는 중 오류가 발생했습니다.", translation.getCode(), e);
			return;
		}

		List<BibleVerse> verses = translation.isNestedBookFormat()
				? parseNestedBookFormat(root, translation.getCode())
				: parseFlatFormat(root, translation.getCode());

		bibleVerseRepository.saveAll(verses);
		log.info("[BibleSeeder] {} {}개 절 시딩 완료", translation.getCode(), verses.size());
	}

	/** 기존 번역본들(NKRV 등)과 KJV가 쓰는 평평한 구조: 절 하나하나가 book(정수)/chapter/verse를 직접 갖는다. */
	private List<BibleVerse> parseFlatFormat(JsonNode array, String translationCode) {
		List<BibleVerse> verses = new ArrayList<>(array.size());

		for (JsonNode obj : array) {
			verses.add(
					BibleVerse.builder()
							.translation(translationCode)
							.bookId(obj.get("book").asInt())
							.chapter(obj.get("chapter").asInt())
							.verse(obj.get("verse").asInt())
							.text(obj.get("text").asString())
							.title(textOrNull(obj, "title"))
							.title2(textOrNull(obj, "title_2"))
							.text2(textOrNull(obj, "text_2"))
							.build()
			);
		}
		return verses;
	}

	/**
	 * NIV, ESV가 쓰는 중첩 구조: 최상위 배열이 책(총 66개, 창세기~요한계시록 순서 그대로)이고,
	 * 각 책 안에 장 목록, 각 장 안에 절 목록이 들어있다.
	 * - bookId는 책 이름이 아니라 최상위 배열의 순서(1번째=창세기=1)를 그대로 쓴다.
	 * - chapter 번호는 "chapter" 필드가 있으면 그대로, 없으면 "ID"(예: "OT:GEN.2")의 마지막 "." 뒤 숫자를 쓴다.
	 * - 같은 장 안에서 같은 절 번호가 연속으로 여러 번 나오면(ESV 특유의 문제) 공백으로 이어붙여 한 절로 합친다.
	 */
	private List<BibleVerse> parseNestedBookFormat(JsonNode array, String translationCode) {
		List<BibleVerse> verses = new ArrayList<>();

		for (int bookIndex = 0; bookIndex < array.size(); bookIndex++) {
			int bookId = bookIndex + 1;
			JsonNode chapters = array.get(bookIndex).get("chapters");

			for (JsonNode chapterObj : chapters) {
				int chapterNum = resolveChapterNumber(chapterObj);
				JsonNode verseArray = chapterObj.get("verses");

				int index = 0;
				while (index < verseArray.size()) {
					JsonNode first = verseArray.get(index);
					String verseNumRaw = first.get("verse").asString();

					StringBuilder textBuilder = new StringBuilder(first.get("text").asString());
					String text2 = textOrNull(first, "text_2");

					int next = index + 1;
					while (next < verseArray.size() && verseNumRaw.equals(verseArray.get(next).get("verse").asString())) {
						JsonNode obj = verseArray.get(next);
						textBuilder.append(' ').append(obj.get("text").asString());
						if (text2 == null) {
							text2 = textOrNull(obj, "text_2");
						}
						next++;
					}

					verses.add(
							BibleVerse.builder()
									.translation(translationCode)
									.bookId(bookId)
									.chapter(chapterNum)
									.verse(Integer.parseInt(verseNumRaw))
									.text(textBuilder.toString())
									.text2(text2)
									.build()
					);

					index = next;
				}
			}
		}
		return verses;
	}

	private int resolveChapterNumber(JsonNode chapterObj) {
		if (chapterObj.has("chapter")) {
			return Integer.parseInt(chapterObj.get("chapter").asString());
		}
		if (chapterObj.has("ID")) {
			String id = chapterObj.get("ID").asString();
			return Integer.parseInt(id.substring(id.lastIndexOf('.') + 1));
		}
		throw new IllegalStateException("장 번호를 알 수 없는 chapter 객체입니다: " + chapterObj);
	}

	private String textOrNull(JsonNode obj, String field) {
		JsonNode node = obj.get(field);
		return (node == null || node.isNull()) ? null : node.asString();
	}
}