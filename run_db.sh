#!/bin/bash

CONTAINER_NAME=bga-mysql

if [ "$(docker ps -q -f name=$CONTAINER_NAME)" ]; then
    echo "MySQL container ($CONTAINER_NAME) đã chạy."
else
    echo "Start MySQL container..."
    docker run --name $CONTAINER_NAME \
        -e MYSQL_ROOT_PASSWORD=root \
        -e MYSQL_DATABASE=bga \
        -p 3306:3306 \
        -d mysql:8.0
    echo "MySQL is running on port 3306."
fi
