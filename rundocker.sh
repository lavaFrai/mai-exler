docker build .
docker stop mai-exler
docker rm mai-exler
docker run -it -d --restart=always -p 8085:80 --name=mai-exler $(docker build -q .)
