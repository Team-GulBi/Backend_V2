# 빌드 스테이지
FROM gradle:7.6-jdk17 AS builder

# 작업 디렉터리 설정 (필요 시)
WORKDIR /home/gradle/project

# gradle 디렉터리 복사
COPY gradle gradle

# 빌드 스크립트 복사
COPY build.gradle .
COPY settings.gradle .

# 소스 복사
COPY src src

# gradlew 권한 부여
RUN chmod +x ./gradlew

# 클린 빌드 수행
RUN ./gradlew clean bootJar

# 최종 이미지
FROM bellsoft/liberica-openjdk-alpine:17

# 빌드 결과물 복사
COPY --from=builder /home/gradle/project/build/libs/*.jar /app.jar

# jar 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]

# 임시 볼륨 설정
VOLUME /tmp
