# bible-data

`BibleSeeder`가 서버 기동 시 이 폴더의 JSON 파일을 읽어 비어있는 번역본만 채웁니다.

## 파일명 규칙 (Android 앱 assets와 동일)

| 파일명 | 번역본 | 구조 |
|---|---|---|
| nkrv.json | 개역개정 | flat |
| krv.json | 개역한글 | flat |
| ksb.json | 표준새번역 | flat |
| klb.json | 현대인의성경 | flat |
| easy.json | 쉬운성경 | flat |
| kjv.json | KJV | flat |
| niv.json | NIV | nested |
| esv.json | ESV | nested |

## 동작 방식
- 이미 데이터가 있는 번역본은 건드리지 않습니다.
- 파일이 없는 번역본은 서버가 죽지 않고 로그만 남기고 건너뜁니다(`WARN [BibleSeeder] ... 시드 파일이 없어 건너뜁니다`).
- 즉, 파일을 하나씩 넣을 때마다 그 번역본만 서버 재시작 시 채워집니다. 한 번에 다 안 넣으셔도 됩니다.

## 확인 방법
서버 실행 후 로그에 `[BibleSeeder] NKRV 31000여개 절 시딩 완료` 같은 로그가 찍히는지 확인하시고,
`GET /api/v1/bibles/1/1?translation=NKRV`로 창세기 1장이 실제로 조회되는지 확인하면 됩니다.