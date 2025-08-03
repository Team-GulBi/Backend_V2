# 1. 빌드 스테이지
FROM gradle:7.6-jdk17 AS builder

WORKDIR /home/gradle/project

# gradlew 복사
COPY gradlew .
# gradle 설정 복사
COPY gradle gradle
# 빌드 스크립트 복사
COPY build.gradle .
COPY settings.gradle .
# 소스코드 복사
COPY src src

# gradlew 실행 권한 부여
RUN chmod +x ./gradlew

# 클린 빌드 및 jar 생성
RUN ./gradlew clean bootJar

# 2. 실행 스테이지
FROM bellsoft/liberica-openjdk-alpine:17

# 빌드된 jar 파일을 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar

# 컨테이너 시작 시 jar 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 임시 디렉터리 마운트용 볼륨 (선택 사항)
VOLUME /tmp
