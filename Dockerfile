# 빌드 전용 이미지
FROM gradle:7-jdk17 AS builder
# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app
# 현재 디렉토리 모든 소스 파일 컨테이너로 복사
COPY . .
# 프로젝트 빌드
RUN ./gradlew clean build -x test

# 실행용 이미지
FROM openjdk:17
# 실행 시 기준 디렉토리 설정
WORKDIR /app
# JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar
# Spring Boot 실행 시 사용할 profile 설정
ENV SPRING_PROFILES_ACTIVE=dev
# 컨테이너 시작 시 실행할 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
