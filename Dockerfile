FROM maven:3.6-jdk-8 
RUN mkdir /app 
ADD . /app/ 
WORKDIR /app
RUN /bin/bash build.sh
