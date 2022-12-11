VERSION=v1
DOCKERUSER=gagankshetty
PROJECT=backend-service
build:
	mvn clean install -Dmaven.test.skip=true
	docker build -f Dockerfile -t ${PROJECT} --platform=linux/arm64 .

push:
	docker tag ${PROJECT} $(DOCKERUSER)/${PROJECT}:$(VERSION)
	docker push $(DOCKERUSER)/${PROJECT}:$(VERSION)
	docker tag ${PROJECT} $(DOCKERUSER)/${PROJECT}:latest
	docker push $(DOCKERUSER)/${PROJECT}:latest

deploy:
	kubectl apply -f ./kubernetes

cleanup:
	kubectl delete -f ./kubernetes

logs:
	kubectl logs -l app=backend-service -f

deploy-gke:
	kubectl apply -f ./kubernetes
	kubectl apply -f ./kubernetes-gke

cleanup-gke:
	kubectl delete -f ./kubernetes
	kubectl delete -f ./kubernetes-gke