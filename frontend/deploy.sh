#!/bin/bash
set -e
cd "$(dirname "$0")"


docker build -t frontend .

if ! docker network ls | grep -q 'sniff-step'; then
    docker network create sniff-step
fi

docker rm -f frontend
docker run -d --restart=always --name frontend --network sniff-step frontend