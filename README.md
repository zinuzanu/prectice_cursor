# 간단 게시판 (Spring Boot + Thymeleaf CRUD)

로컬에서 바로 실행 가능한 **Spring Boot 기반 간단 게시판(CRUD) 웹 애플리케이션**입니다.  
Thymeleaf로 HTML을 서버 사이드 렌더링하고, Spring Data JPA + H2(DB)로 데이터를 저장합니다.

---

## 요구사항(원본 프롬프트 정리)

- **Post 필드**
  - `id` (Long, auto-generated)
  - `title` (String)
  - `content` (String)
  - `createdAt` (LocalDateTime)
- **기능**
  - 게시글 목록 조회 (`/posts`)
  - 게시글 작성 (`/posts/new`)
  - 게시글 상세보기 (`/posts/{id}`)
  - 게시글 수정 (`/posts/{id}/edit`)
  - 게시글 삭제 (`/posts/{id}/delete`)
- **프론트엔드**
  - Thymeleaf 템플릿(목록/작성/상세/수정) 제공
  - Bootstrap CDN으로 기본 스타일 적용
- **프로젝트/환경**
  - Java 21, Spring Boot 3.4.4
  - Spring Web, Thymeleaf, Spring Data JPA, H2 DB
  - H2 콘솔 접속 가능
  - Gradle 기반

출처: [티스토리 포스트](https://joonfluence.tistory.com/861)

---

## 기술 스택

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.4
- **Build**: Gradle (Kotlin DSL)
- **Web**: Spring Web (MVC)
- **Template**: Thymeleaf
- **DB**: H2 (in-memory)
- **ORM**: Spring Data JPA (Hibernate)
- **UI**: Bootstrap 5 (CDN)

추가로, 로컬에 Java 21이 없더라도 빌드가 가능하도록 **Gradle Toolchain + Foojay Resolver** 설정을 포함합니다.

---

## 실행 방법

### 1) 빌드

```bash
./gradlew build
```

### 2) 실행

```bash
./gradlew bootRun
```

### 3) 접속

- 게시판: `http://localhost:8080/posts`
- H2 콘솔: `http://localhost:8080/h2-console`

---

## 화면(Thymeleaf 템플릿)

- **목록**: `src/main/resources/templates/posts/list.html`
- **작성**: `src/main/resources/templates/posts/new.html`
- **상세**: `src/main/resources/templates/posts/detail.html`
- **수정**: `src/main/resources/templates/posts/edit.html`
- **공통 네비게이션**: `src/main/resources/templates/fragments/navbar.html`

---

## 기능 및 URL 목록

| 기능 | Method | URL | 화면/동작 |
|---|---:|---|---|
| 목록 조회 | GET | `/posts` | 목록 화면 |
| 작성 화면 | GET | `/posts/new` | 작성 폼 |
| 작성 처리 | POST | `/posts` | 저장 후 상세로 이동 |
| 상세 보기 | GET | `/posts/{id}` | 상세 화면 |
| 수정 화면 | GET | `/posts/{id}/edit` | 수정 폼 |
| 수정 처리 | POST | `/posts/{id}` | 저장 후 상세로 이동 |
| 삭제 처리 | POST | `/posts/{id}/delete` | 삭제 후 목록으로 이동 |

---

## DB(H2) 설정

`src/main/resources/application.properties` 기준:

- **JDBC URL**: `jdbc:h2:mem:boarddb`
- **username**: `sa`
- **password**: (빈 값)
- **Console Path**: `/h2-console`
- **DDL**: `create-drop` (앱 종료 시 테이블 삭제)

H2 콘솔 접속 시, 위 JDBC URL로 맞춘 뒤 Connect 하면 됩니다.

---

## 핵심 도메인 모델

- `Post`
  - `id`: 자동 증가 PK
  - `title`, `content`: 필수값(빈 값 불가)
  - `createdAt`: 저장 시점 자동 세팅

---

## 프로젝트 구조(요약)

```text
src
└── main
    ├── java/com/example/board
    │   ├── BoardApplication.java
    │   └── post
    │       ├── Post.java
    │       ├── PostController.java
    │       ├── PostService.java
    │       ├── PostRepository.java
    │       ├── PostNotFoundException.java
    │       └── GlobalExceptionHandler.java
    └── resources
        ├── application.properties
        └── templates
            ├── error/not-found.html
            ├── fragments/navbar.html
            └── posts
                ├── list.html
                ├── new.html
                ├── detail.html
                └── edit.html
```

---

## 추가로 있으면 더 좋은 개선 아이디어

- **정렬/페이징**: Spring Data `Pageable` 적용(목록 페이징, 정렬)
- **검증 UX 강화**: 에러 메시지 커스터마이징, 폼 입력값 유지/표시 개선
- **XSS/HTML 처리 정책**: `content` 출력 정책(escape 유지 vs markdown 도입 등) 명확화
- **검색 기능**: 제목/내용 키워드 검색
- **테스트 보강**: Controller 테스트(MockMvc), Repository 테스트(@DataJpaTest)
- **운영 DB 전환**: H2 → MySQL/PostgreSQL 전환 프로필 분리(`application-dev.properties` 등)
- **REST API 확장**: Thymeleaf UI + JSON API 병행 제공

