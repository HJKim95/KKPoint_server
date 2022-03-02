FROM openjdk:8-jdk-alpine

ENV TZ Asia/Seoul
RUN	apk update && apk upgrade && \
	apk add --update tzdata && \
	cp /usr/share/zoneinfo/${TZ} /etc/localtime && \
	echo "${TZ}" > /etc/timezone

ADD build/libs/kk_point-0.0.1-SNAPSHOT.jar app.jar
ARG SPRING_PROFILES_ACTIVE
RUN echo $SPRING_PROFILES_ACTIVE
ENV SPRING_PROFILES_ACTIVE=$SPRING_PROFILES_ACTIVE
ENTRYPOINT ["java","-Djava.awt.headless=true", "-Djava.security.egd=file:/dev/urandom","-jar","/app.jar"]
