docker build .
docker stop mai-exler
docker rm mai-exler
docker run -it -d --restart=always -p 8088:80 -e "JDBC_DATABASE_URL=jdbc:sqlite:database.sqlite" --name=mai-exler $(docker build -q .)
