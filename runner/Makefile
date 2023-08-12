DOCKER_COMPOSE = docker-compose
COMPOSE_FILES = -f docker-compose.yml -f docker-compose-kafka-ui.yml

up:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) up -d

down:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) down

ps:
	$(DOCKER_COMPOSE) $(COMPOSE_FILES) ps

.PHONY: up down ps
