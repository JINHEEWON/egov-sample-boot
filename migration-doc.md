# 전자정부 프레임워크 마이그레이션 프로젝트

## 1. 전환 대상 설명

### 기존 시스템
- **프레임워크**: 전자정부 프레임워크 3.x
- **기술 스택**:
  - Backend: Spring Framework 4.x, MyBatis
  - Frontend: JSP, jQuery
  - Build: Maven
  - Database: H2

### 신규 시스템
- **프레임워크**: Spring Boot 3.x
- **기술 스택**:
  - Backend: Spring Boot 3.x, Spring Data JPA
  - Frontend: Vue 3 + TypeScript
  - Build: Maven (Backend), Vite (Frontend)
  - Database: H2 (개발용)

## 2. 전환 목적 및 목표

### 목적
- 레거시 전자정부 프레임워크 기반 시스템의 현대화
- 개발 생산성 향상
- 유지보수성 개선
- 사용자 경험 개선

### 목표
1. Spring Boot 3.x 기반으로 백엔드 전환
2. Vue 3 기반 SPA로 프론트엔드 전환
3. RESTful API 아키텍처 적용
4. JPA 기반 데이터 액세스 계층 구현
5. JWT 기반 인증 체계 도입

## 3. 전환 방식

### Backend 전환
1. Spring → Spring Boot
   - XML 기반 설정을 Java Config로 전환
   - web.xml 제거 및 SecurityConfig 클래스로 보안 설정 전환
   - MyBatis → JPA 전환으로 SQL 매핑 파일 제거

2. 도메인 모델 전환
   - JPA 엔티티로 도메인 클래스 재설계
   - Lombok 활용으로 코드 간소화
   - 명시적인 타입 지정 및 Null 안정성 강화

3. 데이터 액세스 계층 전환
   - MyBatis mapper → Spring Data JPA repository
   - 동적 쿼리 처리를 위한 Specification 패턴 적용
   - 페이징 처리 개선

### Frontend 전환
1. JSP → Vue 3
   - 컴포넌트 기반 아키텍처 적용
   - TypeScript를 통한 타입 안정성 확보
   - Composition API를 활용한 로직 재사용성 향상

2. 상태 관리
   - Pinia를 활용한 중앙 집중식 상태 관리 구현
   - 인증 상태 및 사용자 정보 관리
   - API 통신 상태 관리

3. 라우팅
   - Vue Router를 활용한 클라이언트 사이드 라우팅
   - 인증 기반 라우트 가드 구현
   - 히스토리 모드 적용

## 4. 주요 이슈 및 해결 과정

### 1. CORS 설정 문제
- **이슈**: 프론트엔드에서 백엔드 API 호출 시 CORS 정책 위반
- **해결**: 
  - SecurityConfig에서 CORS 설정 추가
  - 프론트엔드 개발 서버 프록시 설정
  - withCredentials 옵션 활성화

### 2. JWT 인증 구현
- **이슈**: 세션 기반 인증에서 JWT 기반 인증으로 전환 시 인증 흐름 문제
- **해결**:
  - JwtAuthenticationFilter 구현
  - 토큰 기반 인증 처리 로직 구현
  - 프론트엔드에서 토큰 관리 로직 구현

### 3. API 경로 설정
- **이슈**: 컨텍스트 경로와 API 엔드포인트 중복 문제
- **해결**:
  - 백엔드 컨트롤러 매핑 경로 조정
  - 프론트엔드 API 설정 개선
  - Vite 프록시 설정 활용

## 5. 코드 비교 예시

### Backend: 컨트롤러 구현
```java
// 기존 코드 (JSP 기반)
@Controller
public class EgovSampleController {
    @RequestMapping(value = "/sample/list.do")
    public String list(Model model) {
        model.addAttribute("resultList", sampleService.selectSampleList());
        return "sample/list";
    }
}

// 신규 코드 (REST API)
@RestController
@RequestMapping("/samples")
public class SampleController {
    @GetMapping
    public ResponseEntity<Page<Sample>> getSamples(SampleSearchDto searchDto) {
        return ResponseEntity.ok(sampleService.searchSamples(searchDto));
    }
}
```

### Frontend: 목록 조회
```javascript
<!-- 기존 코드 (JSP) -->
<c:forEach var="result" items="${resultList}">
    <tr>
        <td>${result.title}</td>
        <td>${result.regDate}</td>
    </tr>
</c:forEach>

<!-- 신규 코드 (Vue 3 + TypeScript) -->
<template>
  <div v-for="sample in samples" :key="sample.id">
    <div>{{ sample.title }}</div>
    <div>{{ formatDate(sample.regDate) }}</div>
  </div>
</template>
```

## 6. 향후 개선 사항

### 기능 개선
1. 검색 기능 구현
2. 게시물 수정/삭제 기능 구현
3. 파일 업로드 기능 추가
4. 사용자 권한 관리 기능 구현

### 기술적 개선
1. 테스트 코드 작성
2. API 문서화 (Swagger/OpenAPI)
3. 로깅 및 모니터링 체계 구축
4. 배포 자동화 파이프라인 구축

### UI/UX 개선
1. 반응형 디자인 적용
2. 접근성 가이드라인 준수
3. 다국어 지원
4. 테마 지원

## 7. 참고 사항

### 개발 환경 설정
1. JDK 17 이상 필요
2. Node.js 18 이상 필요
3. VS Code 또는 IntelliJ IDEA 권장
4. Git 버전 관리 시스템 사용

### 문서화
1. API 문서: Swagger UI 통해 제공 예정
2. 개발 가이드: Wiki 페이지 통해 제공 예정
3. 변경 이력: GitHub 저장소에서 관리

### 유의사항
1. 개발 시 TypeScript strict 모드 활성화 필수
2. 커밋 메시지 컨벤션 준수
3. 코드 리뷰 프로세스 준수
4. 보안 가이드라인 준수 