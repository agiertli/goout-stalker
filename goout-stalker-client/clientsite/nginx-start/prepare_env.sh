#sed -i -e "s/.*var BASE_URL=\"http:\/\/localhost:8080\/api\";.*/var BASE_URL=\"http:\/\/$BACKEND_HOSTNAME\/api\" /" ../env.js
echo "revert"