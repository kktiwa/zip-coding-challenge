#!/bin/bash

if [[ -f zookeeper.pid ]]; then
  echo "Zookeeper.pid file exists"
  exit 0
elif [[ -f kafkaServer.pid ]]; then
  echo "Kafka Server File Exists"
  exit 0
fi

bin/zookeeper-server-start.sh config/zookeeper.properties &
echo $! >>zookeeper.pid
sleep 15s
bin/kafka-server-start.sh config/server.properties &
echo $! >>kafkaServer.pid
