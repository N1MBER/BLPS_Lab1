curl -X POST \
  'http://localhost:8080/users/register' \
  -H 'Content-Type: application/json' \
  -d '{
    "email": "lolka@gmail.com",
    "name": "Maxim",
    "password": "Maxim_123",
    "surname": "Kolesnikov"
}'
TOKEN="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb2NpYWxmb3gzM0BnbWFpbC5jb20iLCJleHAiOjE2MTc5MTU2MDB9.vh9horLJ79NrDDsGn25UySFHSl0q1uA7ML-DcQsmqUQ09zW029LIhY_bOeOIv0cDW3yodQtexc2QZigJ3YGtMQ"
#curl -X POST \
#  'http://localhost:8080/users/auth' \
#  -H 'Content-Type: application/json' \
#  -d '{
#    "email": "socialfox33@gmail.com",
#    "name": "Maxim",
#    "password": "Maxim_123",
#    "surname": "Kolesnikov"
#}'
#curl -X PUT \
#  'http://localhost:8080/api/add_product' \
#  -H 'Content-Type: application/json' \
#  'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzb2NpYWxmb3gzM0BnbWFpbC5jb20iLCJleHAiOjE2MTc5MTU2MDB9.vh9horLJ79NrDDsGn25UySFHSl0q1uA7ML-DcQsmqUQ09zW029LIhY_bOeOIv0cDW3yodQtexc2QZigJ3YGtMQ' \
#  -d '{
#  "id": 1
#  "name": "Pencil",
#  "price": 60
#}'
#
#curl -X GET \
#  'http://localhost:8080/api/products' \
#  -H 'Content-Type: application/json'
