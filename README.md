# 스프링 부트 프로젝트

## 프로젝트 개요
이 프로젝트는 스프링 부트를 기반으로 한 웹 애플리케이션입니다.

## 기술 스택
* Java
* Spring Boot
* Spring Security
* OAuth2
* JPA
* MySQL

## 의존성
gradle
dependencies {
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-mustache'
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
compileOnly 'org.projectlombok:lombok'
annotationProcessor 'org.projectlombok:lombok'
runtimeOnly 'com.mysql:mysql-connector-j'
testImplementation 'org.springframework.boot:spring-boot-starter-test'
testImplementation 'org.springframework.security:spring-security-test'
}

## 프로젝트 구조
src/main/java/com/kr/jikim/auth/
├── config/
│ ├── SecurityConfig.java
│ ├── OAuth2AuthorizationServerConfig.java
│ └── OAuth2ResourceServerConfig.java
├── controller/
│ └── AuthController.java
├── entity/
│ └── User.java
├── repository/
│ └── UserRepository.java
├── service/
│ └── UserService.java
├── AuthApplication.java

## 설정
application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_db
spring.datasource.username=root
spring.datasource.password=password

## 실행
./gradlew bootRun

## 빌드
./gradlew build

### 필수 조건
* JDK 17 이상
* MySQL 8.0 이상
* Gradle 7.0 이상

### 주요 기능
1. OAuth2 인증 서버 구현
2. 사용자 관리 기능
3. 데이터베이스 연동
4. 보안 설정
