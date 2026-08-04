# knowledge-data

`KnowledgeSeeder`가 서버 기동 시 이 폴더의 8개 JSON 파일을 각각 읽어, 해당 영역이 비어있을 때만 채웁니다.
Android 앱의 `assets/knowledge/` 파일명·구조를 그대로 씁니다 — **각 파일은 감싸는 객체 없이 최상위가 배열**입니다.

## 파일 목록

| 파일명 | 영역                           |
|---|------------------------------|
| bible_figures.json | 인물사전                         |
| bible_places.json | 지명사전                         |
| genealogy_charts.json | 족보 (안의 `entries` 배열까지 함께)    |
| bible_timeline.json | 연대표                          |
| bible_culture.json | 당시 문화                        |
| parables_miracles.json | 비유와 이적                       |
| topical_verses.json | 상황별 말씀 (안의 `verses` 배열까지 함께) |
| bible_unit.json | 성경의 단위들                      |

## 예시 (bible_figures.json)

```json
[
  { "id": "abraham", "name": "아브라함", "otherNames": "아브람", "category": "족장", "era": "족장 시대", "summary": "믿음의 조상", "description": "...", "keyBookId": 1, "keyChapter": 12, "keyVerseLabel": "창 12:1" }
]
```

## 예시 (genealogy_charts.json — entries 중첩)

```json
[
  {
    "id": "abraham-family",
    "title": "아브라함의 족보",
    "description": "...",
    "keyBookId": 1, "keyChapter": 11, "keyVerseLabel": "창 11:27",
    "entries": [
      { "name": "데라", "relation": "아브라함의 아버지", "note": "..." }
    ]
  }
]
```

## 예시 (topical_verses.json — verses 중첩)

```json
[
  {
    "id": "comfort",
    "title": "위로가 필요할 때",
    "verses": [
      { "bookId": 19, "chapter": 23, "verseStart": 1, "verseEnd": 6 }
    ]
  }
]
```

- `id`는 문자열(slug)입니다.
- 파일이 없는 영역은 건너뜁니다. 영역별로 이미 데이터가 있으면(해당 테이블 기준) 그 영역만 다시 시딩하지 않습니다 — 즉 파일을 하나씩 넣어도 그때그때 그 영역만 채워집니다.