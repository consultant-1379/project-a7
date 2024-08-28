#!/bin/sh

java -agentlib:jdwp=transport=dt_socket,address=8002,server=y,suspend=n -jar /usr/app/report-generator-1.0-SNAPSHOT.jar &
java -agentlib:jdwp=transport=dt_socket,address=8004,server=y,suspend=n -jar /usr/app/user-interaction-1.0-SNAPSHOT.jar
