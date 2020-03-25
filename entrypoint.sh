#!/bin/sh
APP_JAR=$(ls *.jar)
command="java -jar $APP_JAR --spring.profiles.active=prod"
echo $command
exec $command