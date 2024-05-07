docker build .
docker stop exler-mai
docker rm exler-mai
docker run -it -d --restart=always -p 8085:80 --name=exler-mai $(docker build -q .)
