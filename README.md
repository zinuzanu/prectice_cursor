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

<div align=center><h1>📚 STACKS</h1></div>

<div align=center> 
  <img src="https://img.shields.io/badge/Java-21-007396?style=for-the-badge&logo=openjdk&logoColor=white"> 
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Spring%20MVC-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
  <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white">
  <br>
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
  <img src="https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
  <img src="https://img.shields.io/badge/H2-1F6FEB?style=for-the-badge&logo=h2&logoColor=white">
  <br>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
  <img src="https://img.shields.io/badge/Bootstrap-5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white">
  <br>
  <img src="https://img.shields.io/badge/JUnit%205-25A162?style=for-the-badge&logo=junit5&logoColor=white">
  <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
  <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white">
  <br>
</div>

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

## 화면(Thymeleaf + IDE 레이아웃)

- **목록**: `src/main/resources/templates/posts/list.html` (검색/페이징, IDE 레이아웃)
- **작성**: `src/main/resources/templates/posts/new.html` (글자수 카운터, textarea 자동 확장)
- **상세**: `src/main/resources/templates/posts/detail.html` (IDE 스타일 헤더/메타 정보)
- **수정**: `src/main/resources/templates/posts/edit.html` (작성화면과 동일 UX)
- **에러(404)**: `src/main/resources/templates/error/not-found.html` (IDE 레이아웃)
- **IDE 레이아웃**: `src/main/resources/templates/fragments/ide-layout.html`

---

## 기능 및 URL 목록

| 기능 | Method | URL | 화면/동작 |
|---|---:|---|---|
| 목록 조회(검색/페이징) | GET | `/posts` | 목록 화면 (`q`, `page`, `size`, `sort` 지원) |
| 작성 화면 | GET | `/posts/new` | 작성 폼 |
| 작성 처리 | POST | `/posts` | 저장 후 상세로 이동 |
| 상세 보기 | GET | `/posts/{id}` | 상세 화면 |
| 수정 화면 | GET | `/posts/{id}/edit` | 수정 폼 |
| 수정 처리 | POST | `/posts/{id}` | 저장 후 상세로 이동 |
| 삭제 처리 | POST | `/posts/{id}/delete` | 삭제 후 목록으로 이동 |

---

## JSON API (REST)

Thymeleaf UI와 별개로, JSON 기반 CRUD API도 제공합니다.

| 기능 | Method | URL | 비고 |
|---|---:|---|---|
| 목록(검색/페이징) | GET | `/api/posts` | `q`, `page`, `size`, `sort` 지원 |
| 상세 | GET | `/api/posts/{id}` |  |
| 생성 | POST | `/api/posts` | body: `{ "title": "...", "content": "..." }` |
| 수정 | PUT | `/api/posts/{id}` | body: `{ "title": "...", "content": "..." }` |
| 삭제 | DELETE | `/api/posts/{id}` | 204 No Content |

---

## DB(H2) 설정

`src/main/resources/application.properties` 기준:

- **JDBC URL**: `jdbc:h2:mem:boarddb`
- **username**: `sa`
- **password**: (빈 값)
- **Console Path**: `/h2-console`
- **DDL**: `create-drop` (앱 종료 시 테이블 삭제)

H2 콘솔 접속 시, 위 JDBC URL로 맞춘 뒤 Connect 하면 됩니다.

### 프로필 분리(운영 DB 전환용 템플릿)

- `application-dev.properties`: H2 파일 DB (재시작해도 데이터 유지)
- `application-mysql.properties`: MySQL 설정 템플릿
- `application-postgres.properties`: PostgreSQL 설정 템플릿

예) dev 프로필로 실행:

```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

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
    │   └── web
    │       └── GlobalModelAttributes.java
    └── resources
        ├── application.properties
        ├── application-*.properties
        ├── messages.properties
        ├── static
        │   ├── css/ide.css
        │   └── js/ide.js
        └── templates
            ├── error/not-found.html
            ├── fragments
            │   ├── navbar.html
            │   └── ide-layout.html
            └── posts
                ├── list.html
                ├── new.html
                ├── detail.html
                └── edit.html
```

---
