docker-compose down
cd goout-stalker-backend && mvn clean package -DskipTests
docker build -t goout-stalker-backend .