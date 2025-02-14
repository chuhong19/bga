#!/bin/bash

CONTAINER_NAME=bga-server

# Check mySQL container running
if [ "$(docker ps -q -f name=bga-mysql)" ]; then
    echo "MySQL is running, continue to start server..."
else
    echo "MySQL has not run. Run ./run_db.sh first!"
    exit 1
fi

# Build project
echo "Building Spring Boot application..."
./gradlew build -x test

# Build Docker image
echo "Building Docker image for Spring Boot..."
docker build -t bga-server:latest .

docker rm -f $CONTAINER_NAME 2>/dev/null

echo "Start Spring Boot container..."
docker run --name $CONTAINER_NAME \
    -p 9100:9100 \
    --link bga-mysql:mysql \
    -d bga-server:latest

echo "Spring Boot server is running on port 9100."

docker logs -f $CONTAINER_NAME