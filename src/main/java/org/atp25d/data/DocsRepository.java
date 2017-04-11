package org.atp25d.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;
import org.atp25d.model.LocationNote;
import org.atp25d.model.UserAccess;
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
    public boolean hasReadAccess(String loginId) {
    	 return hasTaskAccess(loginId,"Read");
    }
    
	 public boolean hasTaskAccess(String loginId, String level) {
		 if (loginId==null || loginId.trim().equals("")) return false;
	   	 TypedQuery<UserAccess> query =
			      em.createNamedQuery("UserAccess.findUserAccess", UserAccess.class);		  
		  query.setParameter("userId", loginId);
		  query.setParameter("task", "Doctors");		  	    
		 List<UserAccess> rows = query.getResultList();
		 for (UserAccess row : rows)
		 {
			 if (row.getUserAccess().equalsIgnoreCase(level)){
				return true; 
			 }
		 }       	    	
		return false; 
	}	
	public List<DoctorNote> findDocNotes(int doctorId) {
		TypedQuery<DoctorNote> query = em.createNamedQuery("DoctorNote.findByDocId",
				DoctorNote.class);
		  query.setParameter("doctorId", doctorId);		
		return query.getResultList();
	}
	public List<LocationNote> findLocNotes(int locationNumber) {
		TypedQuery<LocationNote> query = em.createNamedQuery("LocationNote.findByLocId",
				LocationNote.class);
		  query.setParameter("locationNumber", locationNumber);		
		return query.getResultList();
	}	 	
}
