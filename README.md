# Examples using curl

### list
```
curl http://localhost:8080/customers

[]
```


### add
```
curl -X POST -H "Content-Type: application/json" -d '{"name":"Fulano"}' http://localhost:8080/customers

[{"id":1,"name":"Fulano"}]
```

### modify
```
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Fulano Modificado"}' http://localhost:8080/customers/1

[{"id":1,"name":"Fulano Modificado"}]
```

### delete
```
curl -X DELETE http://localhost:8080/customers/1
```

