# Spring Boot application sample using Fluent Query


It's a simple rest API that has a customer table:

```
	@GetMapping("/customers/region/{code}")
	public List<Customer> findByRegionCode(@PathVariable("code") String regionCode) {
		return repository.list(
				repository.query(Customer.class)
					.where(x -> x.getRegionCode()).eq(regionCode)
		);
	}
```

The project was created using the Spring Initializr and it was added the fluent query library.

### list
```
curl http://localhost:8080/customers

[]
```


### add
```
curl -X POST -H "Content-Type: application/json" -d '{"name":"Fulano"}' http://localhost:8080/customers

[{"id":1,"name":"Fulano","regionCode":null}]
```

### modify
```
curl -X PUT -H "Content-Type: application/json" -d '{"name":"Fulano Modificado"}' http://localhost:8080/customers/1

[{"id":1,"name":"Fulano Modificado","regionCode":null}]
```

### delete
```
curl -X DELETE http://localhost:8080/customers/1
```

