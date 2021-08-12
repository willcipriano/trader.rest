setup:
	docker-compose -f docker-compose.local.yml up -d

teardown:
	docker-compose -f docker-compose.local.yml down

install:
	./mvnw clean install -Dspring.profiles.active=local

clean:
	./mvnw clean -Dspring.profiles.active=local

run:
	./mvnw spring-boot:run -Dspring-boot.run.profiles=local

docker.run:
	docker-compose up

docker.stop:
	docker-compose stop

docker.build:
	./mvnw clean
	docker-compose rm --stop --force -v
	docker-compose build --no-cache

docker.test:
	./mvnw clean
	docker-compose rm --stop --force -v
	docker-compose build --no-cache
	docker-compose -f docker-compose.test.yml up --abort-on-container-exit

docker.clean:
	./mvnw clean
	docker-compose rm --stop --force -v