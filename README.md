# musinsa-category

## 구성
- java11, Spring Boot, gradle, H2, JPA, QueryDSL등을 사용하여 과제 수행하였습니다.
- 상품 카테고리 테이블은 다음과 같습니다.
<img width="346" alt="스크린샷 2021-12-15 오후 5 45 26" src="https://user-images.githubusercontent.com/26237035/146153455-b6455375-65d9-4c83-bac5-affd4eaceb4e.png">

## RestAPI
- GET /productCategories 
> 모든 상품카테고리를 조회한다.

- GET /productCategories/{productCategoryCode}
> `{productCategory}`에 해당하는 상품카테고리를 조회한다. 하위 항목이 있는 경우 포함하여 반환한다.

- POST /productCategories
> 최상위 상품카테고리를 등록한다.

- POST /productCategories/{productCategoryCode}
> `{productCategoryCode}`에 해당하는 상품카테고리의 하위 카테고리를 등록한다.

- PUT /productCategories/{productCategoryCode}
> `{productCategoryCode}`에 해당하는 상품카테고리를 수정한다.

- DELETE /productCategories/{productCategoryCode}
> `{productCategoryCode}`에 해당하는 상품카테고리를 삭제한다. 하위 항목이 있는 경우 같이 삭제한다.

## 리눅스 환경 배포 과정

1. gradle bootJar task를 이용하여 jar파일을 생성 후 리눅스 서버로 파일을 옮긴다.
2. 리눅스 서버의 java version을 확인한다. 
- `java --version`
3. java가 설치되어있지 않거나 버전이 11이 아닌 경우 jdk를 설치한다.
- `yum install -y java-11-openjdk-devel`
4. 1번에서 옮긴 jar파일을 실행한다.
- `java –jar musinsa-category.jar`
