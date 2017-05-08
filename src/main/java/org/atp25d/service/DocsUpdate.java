package org.atp25d.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.atp25d.data.DocsRepository;
import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;
import org.atp25d.model.LocationNote;
import org.atp25d.model.UserAccess;
import org.jboss.as.quickstarts.kitchensink.model.Member;


@Stateless

public class DocsUpdate {
	
    @Inject
    private EntityManager em;
    
	@Inject
	private DocsRepository docsRepository;
	
    @Inject
    private Event<Location> locationEventSrc;    
    
    @Inject
    private Event<Doctor> doctorEventSrc;    
    
    public Doctor getDocForUpdate(Doctor doc){
		return em.find(Doctor.class, doc.getDoctorNumber());    	
    }
    public DoctorNote getDocNoteForUpdate(DoctorNote docNote){
		return em.find(DoctorNote.class, docNote.getNoteId());    	
    }
    public LocationNote getLocNoteForUpdate(LocationNote locNote){
		return em.find(LocationNote.class, locNote.getNoteId());    	
    }
    public boolean docExists(Doctor doc){
		TypedQuery<Doctor> query = em.createNamedQuery("Doctor.findMatchDoc", Doctor.class);
		query.setParameter("surname", doc.getSurname());
		query.setParameter("firstName", doc.getFirstName());
		query.setParameter("middleName", doc.getMiddleName());
		query.setParameter("title", doc.getTitle());
		query.setParameter("emailAddress", doc.getEmailAddress());
		query.setParameter("location", doc.getLocation());
		List<Doctor> docs = query.getResultList();
		return !docs.isEmpty();
    }
    public void saveDoc(Doctor doc){
    	if (doc.getDoctorId() == 0) {
    		doc.setDoctorId(getNextDoctorId());
    	}
		em.merge(doc);		
		TypedQuery<Doctor> q = em.createNamedQuery("updateOtherDocs", Doctor.class);
		q.setParameter(1, doc.getTitle());
		q.setParameter(2, doc.getFirstName());
		q.setParameter(3, doc.getMiddleName());
		q.setParameter(4,doc.getSurname());
		q.setParameter(5,doc.getQuals());
		q.setParameter(6,doc.getEmailAddress());
		q.setParameter(7,doc.getPhoneNumber());
		q.setParameter(8,doc.getMobileNumber());
		q.setParameter(9,doc.getCategory());
		q.setParameter(10, doc.getDoctorNumber());
		q.setParameter(11, doc.getDoctorId());
		q.executeUpdate();	    
		List<Doctor> docs = docsRepository.getDoctorsById(doc.getDoctorId());
		for (Doctor doc1 : docs) 
		{
			doctorEventSrc.fire(doc1);
		}
   		
    }
    public Location getLocForUpdate(Location loc){
		return em.find(Location.class, loc.getLocationNumber());    	
    }
    public void saveLoc(Location loc){
		em.merge(loc);    	
		locationEventSrc.fire(loc);
    }    
    public void saveDocNote(DoctorNote docNote){
		em.merge(docNote);    			
    }
    public void saveLocNote(LocationNote locNote){
		em.merge(locNote);    			
    }            
	private int getNextDoctorId(){
		 Query q = em.createNamedQuery("Doctor.findLastDocId");				
		 int id = (Integer) (q.getSingleResult());
		 return ++id;
	}	     
}
