package com.naskar.sample.spring.fluentquery;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import com.naskar.fluentquery.jpa.dao.impl.DAOImpl;
import com.naskar.fluentquery.metamodel.conventions.MetamodelConvention;

@Repository
public class FluentRepository extends DAOImpl {
	
	private EntityManager entityManager;

	public FluentRepository(EntityManager entityManager) {
		
		this.entityManager = entityManager;
		
		var mc = new MetamodelConvention();
	    mc.addAll(entityManager);
	    
	    this.setEm(entityManager);
	    this.setConvention(mc);
		
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
