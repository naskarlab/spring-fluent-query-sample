package com.naskar.sample.spring.fluentquery;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@RestController
public class CustomerController {

	private FluentRepository repository;

	public CustomerController(FluentRepository repository) {
		this.repository = repository;
	}

	@GetMapping("/customers")
	public List<Customer> listAll() {
		return repository.list(repository.query(Customer.class));
	}
	
	@GetMapping("/customers/{id}")
	public Customer findById(@PathVariable("id") Long id) {
		return repository.single(
				repository.query(Customer.class)
					.where(x -> x.getId()).eq(id)
		);
	}
	
	@PostMapping("/customers")
	public Customer add(@RequestBody Customer customer) {
		repository.getEntityManager().persist(customer);
		return customer;
	}
	
	@PutMapping("/customers/{id}")
	public Customer modify(@RequestBody Customer customer, @PathVariable("id") Long id) {
		var customerFound = repository.single(
				repository.query(Customer.class)
					.where(x -> x.getId()).eq(id)
			);
		
		if (customerFound != null) {
			customerFound.setName(customer.getName());
			repository.getEntityManager().merge(customerFound);
		}
		
		return customerFound;
	}
	
	@DeleteMapping("/customers/{id}")
	public void remove(@PathVariable("id") Long id) {
		repository.delete(
				repository.single(
						repository.query(Customer.class)
						.where(x -> x.getId()).eq(id)
				)
		);
	}
}
