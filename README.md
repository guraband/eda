# docker 네트워크 생성
docker network create kafka-network

# Kafka 실행
docker compose -p kafka -f kafka-compose.yml up -d
docker compose -p kafka -f kafka-compose.yml down

# Spring Boot 실행
docker compose -p eda -f eda-compose.yml up -d
docker compose -p eda -f eda-compose.yml down

# Kafka 관련
```bash
# docker log
docker compose logs -f

# 토픽 생성
docker exec -it kafka1 kafka-topics --create --bootstrap-server localhost:9092 --topic product_tags_added

# 토픽 생성
docker exec -it kafka1 kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 3 \
  --partitions 1 \
  --topic product_tags_added

docker exec -it kafka1 kafka-topics --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 3 \
  --partitions 1 \
  --topic product_tags_removed

# 토픽 조회
docker exec -it kafka1 kafka-topics --list --bootstrap-server localhost:9092

# 토픽 삭제
docker exec -it kafka1 kafka-topics --delete --bootstrap-server localhost:9092 --topic product_tags_added

docker exec -it kafka1 kafka-topics --delete --bootstrap-server localhost:9092 --topic product_tags_removed

# producer
docker exec -it kafka1 kafka-console-producer --bootstrap-server localhost:9092 --topic product_tags_added

# consumer
docker exec -it kafka1 kafka-console-consumer --bootstrap-server localhost:9092 --topic product_tags_added

# 파티션 리더 확인
docker exec -it kafka1 kafka-topics --bootstrap-server localhost:9092 --describe --topic product_tags_added

```