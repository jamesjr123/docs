package org.atp25d.data;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;
import org.atp25d.model.LocationNote;
import org.atp25d.model.UserAccess;
import org.atp25d.model.UserProfile;
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
	public List<Doctor> findAllDocsLoc(Location loc) {

		TypedQuery<Doctor> query = em.createNamedQuery("Doctor.findByLoc",
				Doctor.class);
		  query.setParameter("locationNumber", loc.getLocationNumber());		
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
	public List<DoctorNote> findMyDocNotes(String user) {
		TypedQuery<DoctorNote> query = em.createNamedQuery("DoctorNote.findByUserId",
				DoctorNote.class);
		  query.setParameter("userId", user);		
		return query.getResultList();
	}
	public UserProfile findUserProfile(String user) {
		TypedQuery<UserProfile> query = em.createNamedQuery("UserProfile.findById",
				UserProfile.class);
		  query.setParameter("userId", user);	
		if (query.getResultList().isEmpty()) return new UserProfile();
		return query.getResultList().iterator().next();
	}	
	public List<DoctorNote> findMyDocNotesByDate(String user, Date from, Date to) {
		TypedQuery<DoctorNote> query = em.createNamedQuery("DoctorNote.findByByUserDate",
				DoctorNote.class);
		  query.setParameter("userId", user);		
		  query.setParameter("fromDate", from);
		  query.setParameter("toDate", to);
		return query.getResultList();
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
	public Doctor getDoctorDetails(int doctorNumber) {
		TypedQuery<Doctor> query = em.createNamedQuery("Doctor.findByNumber", Doctor.class);
		query.setParameter("doctorNumber", doctorNumber);
		List<Doctor> docs = query.getResultList();
		if (!docs.isEmpty()) {
			return (docs.listIterator().next());			
		}
		Doctor doc = new Doctor();
		doc.setDisplayName("Not found");
		return doc;			
	}
	public List<Doctor> getDoctorsById(int doctorId) {
		TypedQuery<Doctor> query = em.createNamedQuery("Doctor.findById", Doctor.class);
		query.setParameter("doctorId", doctorId);
		return query.getResultList();
	}	
}
