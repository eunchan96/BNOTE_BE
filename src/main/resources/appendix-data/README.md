# appendix-data

`AppendixService`가 서버 기동 시 이 폴더의 4개 JSON 파일을 각각 읽어 메모리에 캐싱합니다.
Android 앱의 `assets/appendix/` 파일명·구조를 그대로 씁니다. DB 테이블은 쓰지 않습니다.

## 파일 목록

| 파일명 | 내용 |
|---|---|
| lords_prayer.json | 주기도문 (여러 번역본) |
| apostles_creed.json | 사도신경 (여러 번역본) |
| ten_commandments.json | 십계명 |
| responsive_readings.json | 교독문 (여러 편, 번호로 구분) |

## 예시 (lords_prayer.json / apostles_creed.json — 공통 구조)

```json
{
  "title": "주기도문",
  "versions": [
    { "id": "standard", "label": "표준", "lines": ["하늘에 계신 우리 아버지여", "..."] }
  ]
}
```

## 예시 (ten_commandments.json)

```json
{
  "title": "십계명",
  "intro": ["하나님이 이 모든 말씀으로 말씀하여 이르시되"],
  "commandments": [
    { "number": 1, "text": "나 외에는 다른 신들을 네게 있게 하지 말라" }
  ],
  "reference": "출 20:1-17",
  "summary": { "text": "하나님을 사랑하고 이웃을 사랑하라", "reference": "마 22:37-40" }
}
```

## 예시 (responsive_readings.json — 여러 편 배열)

```json
[
  {
    "number": 1,
    "title": "창조",
    "lines": [
      { "speaker": "leader", "text": "태초에 하나님이 천지를 창조하시니라" },
      { "speaker": "congregation", "text": "땅이 혼돈하고 공허하며" }
    ]
  }
]
```
- `speaker`는 `leader`(인도자) / `congregation`(회중) / `unison`(다같이) 중 하나입니다.

## 동작 방식
- 4개 파일 중 없는 파일은 건너뜁니다 (해당 API 호출 시 404).
- 서버 기동 시 한 번만 읽어 메모리에 올려두고 그대로 서빙합니다.