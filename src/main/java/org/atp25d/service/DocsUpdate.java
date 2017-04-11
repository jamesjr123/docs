package org.atp25d.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;
import org.jboss.as.quickstarts.kitchensink.model.Member;


@Stateless

public class DocsUpdate {
	
    @Inject
    private EntityManager em;

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
    public void saveDoc(Doctor doc){
    	if (doc.getDoctorId() == 0) {
    		doc.setDoctorId(getNextDoctorId());
    	}
		em.merge(doc);    	
		doctorEventSrc.fire(doc);
    }
    public Location getLocForUpdate(Location loc){
		return em.find(Location.class, loc.getLocationNumber());    	
    }
    public void saveLoc(Location loc){
		em.merge(loc);    	
		locationEventSrc.fire(loc);
    }    
    public void saveNote(DoctorNote docNote){
		em.merge(docNote);    			
    }        
	private int getNextDoctorId(){
		 Query q = em.createNamedQuery("Doctor.findLastDocId");				
		 int id = (Integer) (q.getSingleResult());
		 return ++id;
	}	     
}
