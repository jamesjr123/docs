package org.atp25d.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.atp25d.model.Doctor;
import org.atp25d.model.Location;
import org.jboss.as.quickstarts.kitchensink.model.Member;


@ApplicationScoped

public class DocsRepository {

    @Inject
    private EntityManager em;
    
	public List<Doctor> findAllDocs() {

		TypedQuery<Doctor> query = em.createNamedQuery("Doctor.findAll",
				Doctor.class);
		return query.getResultList();
	}

	public List<Location> findAllLocations() {
		TypedQuery<Location> query = em.createNamedQuery("Location.findAll",
				Location.class);
		return query.getResultList();
	} 
    
}
