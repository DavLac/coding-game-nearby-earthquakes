#!/bin/sh

echo "Building docker images..."

./gradlew -Pprod bootJar jibDockerBuild

echo -e "\nStarting containers...\n"

docker-compose -p container -f app.yml up -d

read -n 1 -s -r -p "
----------------------------------------------------------
Application 'nearby-earthquakes' is running! Access URLs:
Local: 		http://localhost:8081/
Profile(s): 	[swagger, dev]

Get nearby earthquakes :
GET http://localhost:8081/api/earthquakes/nearby?latitude=40.730610&longitude=-73.935242
----------------------------------------------------------

Use docker stop command to stop application.

Please wait 30sec application is starting. Press any keys to continue...
"
