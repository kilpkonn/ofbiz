FROM ubuntu:18.04

MAINTAINER tutinformatics

# copy files to workdir
ADD . /ofbiz
WORKDIR /ofbiz

# Install OpenJDK-11
RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/
RUN export JAVA_HOME
ENV JAVA_OPTS="-Dfile.encoding=UTF-8"

# Fix line endings
RUN apt-get install dos2unix
RUN dos2unix ./gradlew

# Run ofbiz
ENTRYPOINT ./gradlew cleanAll loadAll ofbiz

