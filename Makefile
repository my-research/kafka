DOCKER_COMPOSE = docker-compose
DOCKER_DIR = ./docker/
COMPOSE_FILES = -f $(DOCKER_DIR)docker-compose.yml -f $(DOCKER_DIR)docker-compose-kafka-ui.yml

up:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) up -d

down:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) down

ps:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) ps

.PHONY: up down ps
