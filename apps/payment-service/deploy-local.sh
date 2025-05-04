#!/usr/bin/env bash
set -euo pipefail

### Configuration
APP_NAME="payment-service-app"
APP_DOCKERFILE="Dockerfile"           # adjust if you have a custom path
APP_IMAGE="${APP_NAME}:latest"
APP_CONTAINER="${APP_NAME}-ctr"

# Postgres settings
PG_CONTAINER="payment-service-pg"
PG_IMAGE="postgres:17-alpine"
PG_NETWORK="payment-network"
PG_VOLUME="payment-pgdata"
PG_DB="paymentdb"
PG_USER="paymentuser"
PG_PASS="paymentpass"
PG_PORT=5434

### 1) Maven build
echo "==> Building JAR via Maven…"
mvn clean package -DskipTests

### 2) Build Docker image for the app
echo "==> Building Docker image '${APP_IMAGE}'…"
docker build --no-cache -f "${APP_DOCKERFILE}" -t "${APP_IMAGE}" .

### 3) Ensure Docker network
if ! docker network ls --format '{{.Name}}' | grep -q "^${PG_NETWORK}\$"; then
  echo "==> Creating Docker network '${PG_NETWORK}'…"
  docker network create "${PG_NETWORK}"
else
  echo "==> Docker network '${PG_NETWORK}' already exists."
fi

### 4) Run Postgres container
if [ "$(docker ps -aq -f name=^/${PG_CONTAINER}$)" = "" ]; then
  echo "==> Launching Postgres container '${PG_CONTAINER}'…"
  docker run -d \
    --name "${PG_CONTAINER}" \
    --network "${PG_NETWORK}" \
    -p ${PG_PORT}:5432 \
    -e POSTGRES_DB="${PG_DB}" \
    -e POSTGRES_USER="${PG_USER}" \
    -e POSTGRES_PASSWORD="${PG_PASS}" \
    -v "${PG_VOLUME}":/var/lib/postgresql/data \
    "${PG_IMAGE}"
else
  echo "==> Postgres container '${PG_CONTAINER}' already exists."
  if [ "$(docker ps -q -f name=^/${PG_CONTAINER}$)" = "" ]; then
    echo "    Starting existing Postgres container…"
    docker start "${PG_CONTAINER}"
  fi
fi

### 5) Run the app container
# If it exists, remove it so we can recreate with the new image
if [ "$(docker ps -aq -f name=^/${APP_CONTAINER}$)" != "" ]; then
  echo "==> Removing old app container '${APP_CONTAINER}'…"
  docker rm -f "${APP_CONTAINER}"
fi

echo "==> Launching app container '${APP_CONTAINER}'…"
docker run -d \
  --name "${APP_CONTAINER}" \
  --network "${PG_NETWORK}" \
  -p 8082:8080 \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://${PG_CONTAINER}:5432/${PG_DB}" \
  -e SPRING_DATASOURCE_USERNAME="${PG_USER}" \
  -e SPRING_DATASOURCE_PASSWORD="${PG_PASS}" \
  "${APP_IMAGE}"

echo "==> All done!  
- App available: http://localhost:8082  
- Postgres on localhost:${PG_PORT}, db=${PG_DB}, user=${PG_USER}/${PG_PASS}"
