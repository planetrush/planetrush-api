# 베이스 이미지를 설정합니다.
FROM openjdk:17-jdk

# ARG 설정
ARG JAR_PATH=./build/libs/

# JAR 파일을 컨테이너의 지정된 경로에 복사합니다.
COPY ${JAR_PATH}planetrush-0.0.1-SNAPSHOT.jar /app/planetrush-0.0.1-SNAPSHOT.jar

# .env 파일을 컨테이너의 지정된 경로에 복사합니다.
COPY .env /app/.env

# WORKDIR 설정
WORKDIR /app

# Docker run 시 실행할 명령어를 설정합니다.
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "-Duser.timezone=Asia/Seoul", "/app/planetrush-0.0.1-SNAPSHOT.jar"]

# ENV 파일을 환경 변수로 설정합니다.
ENV JAVA_OPTS="-Dspring.config.location=file:/app/.env"