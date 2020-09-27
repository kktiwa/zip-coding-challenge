curl -i -X PUT http://localhost:8083/connectors/SINK_MYSQL/config \
     -H "Content-Type: application/json" \
     -d '{
               "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
               "tasks.max":1,
               "topics":"P_USERS",
           "insert.mode":"insert",
               "connection.url":"jdbc:mysql://mysql:3306/TEST",
               "connection.user":"TEST",
               "connection.password":"password",
               "auto.create":true
         }'
