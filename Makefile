VERSION=v1
DOCKERUSER=gagankshetty
PROJECT=backend-service

build:
	mvn clean install
	docker build -f Dockerfile -t ${PROJECT} .

push:
	docker tag ${PROJECT} $(DOCKERUSER)/${PROJECT}:$(VERSION)
	docker push $(DOCKERUSER)/${PROJECT}:$(VERSION)
	docker tag ${PROJECT} $(DOCKERUSER)/${PROJECT}:latest
	docker push $(DOCKERUSER)/${PROJECT}:latest
