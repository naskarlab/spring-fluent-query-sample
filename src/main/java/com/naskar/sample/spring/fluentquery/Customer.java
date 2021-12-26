package com.naskar.sample.spring.fluentquery;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CUSTOMER")
public class Customer {

	@Id
	@Column(name = "ID_CUSTOMER")
	@GeneratedValue
	private Long id;

	@Column(name = "NM_NAME")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}