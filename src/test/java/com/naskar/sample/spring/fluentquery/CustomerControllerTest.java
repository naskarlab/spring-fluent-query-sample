package com.naskar.sample.spring.fluentquery;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private EntityManager entityManager;
	
	@Test
	@Transactional
	public void successListAll() throws Exception {
		
		var customer = new Customer();
		customer.setName("Fulano");
		entityManager.persist(customer);
		entityManager.flush();
		
		mvc.perform(get("/customers"))
				.andExpect(jsonPath("$[*].name", notNullValue()));
		
	}
	
	@Test
	@Transactional
	public void successFindById() throws Exception {
		
		var customer = new Customer();
		customer.setName("Fulano");
		entityManager.persist(customer);
		entityManager.flush();
		
		mvc.perform(get("/customers/" + customer.getId()))
				.andExpect(jsonPath("$.name", is("Fulano")));
		
	}
	
	@Test
	public void successAdd() throws Exception {
		var customer = new Customer();
		customer.setName("Ciclano");
		
		mvc.perform(post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(customer)))
				.andExpect(jsonPath("$.name", is("Ciclano")));
		
	}
	
	@Test
	@Transactional
	public void successModify() throws Exception {
		
		var customer = new Customer();
		customer.setName("Fulano");
		entityManager.persist(customer);
		entityManager.flush();
		
		customer.setName("Fulano " + customer.hashCode());
		
		mvc.perform(put("/customers/" + customer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(customer)))
				.andExpect(jsonPath("$.name", is(customer.getName())));
		
	}
	
	@Test
	@Transactional
	public void successRemove() throws Exception {
		
		var customer = new Customer();
		customer.setName("Fulano");
		entityManager.persist(customer);
		entityManager.flush();
		
		mvc.perform(get("/customers/" + customer.getId()))
			.andExpect(jsonPath("$.name", is(customer.getName())));
		
		mvc.perform(delete("/customers/" + customer.getId()))
			.andExpect(status().isOk());
		
		mvc.perform(get("/customers/" + customer.getId()))
			.andExpect(content().string(""));
		
	}
	
	public byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}