# hymn-data

`HymnSeeder`가 서버 기동 시 이 폴더의 `hymns.json`을 읽어 찬송가 데이터가 비어있을 때만 채웁니다.

## 파일 구조 (hymns.json)

```json
{
  "majorCategories": [
    { "id": 1, "name": "예배", "sortOrder": 0 }
  ],
  "minorCategories": [
    { "id": 1, "majorId": 1, "name": "찬양과 경배", "sortOrder": 0 }
  ],
  "hymns": [
    {
      "number": 1,
      "title": "만복의 근원 하나님",
      "categoryId": 1,
      "image": "001_1.jpg|001_2.jpg",
      "youtubeSong": "https://youtube.com/...",
      "youtubeMr": "https://youtube.com/..."
    }
  ]
}
```

- `majorCategories`/`minorCategories`/`hymns` 안의 `id`, `majorId`, `categoryId`는 **이 파일 안에서만 유효한 논리 id**입니다.
  실제 DB에 들어갈 때는 대분류→소분류 순서로 새로 생성되는 id로 자동 매핑되니, 파일 안에서 번호가 겹치는 것은 걱정하지 않으셔도 됩니다(대분류/소분류가 둘 다 1부터 시작해도 문제 없음).
- `image` 필드는 악보가 여러 장이면 `|`로 구분합니다 (예: `"001_1.jpg|001_2.jpg"`).
- 이미 데이터가 있으면 건드리지 않습니다. 파일이 없어도 서버는 정상 기동되고 로그만 남습니다.

## ⚠️ 실제 악보 이미지 파일은 여기 넣지 마세요
이 폴더(`src/main/resources/hymn-data/`)는 `hymns.json` 텍스트 메타데이터 전용입니다.
악보 이미지 원본은 **`{file.upload.path}/hymns/images/`**(로컬 기본값 `./uploads/hymns/images/`)에 넣어주세요.
- API 응답(`HymnResponse.imageUrls`)이 `/uploads/hymns/images/{파일명}`으로 내려가고, `WebMvcConfig`가 이 경로를 정적 파일로 서빙합니다.
- 여기(클래스패스)에 넣으면 이미지 하나 바꿀 때마다 서버를 다시 빌드·배포해야 해서, 파일시스템 경로로 분리해뒀습니다.