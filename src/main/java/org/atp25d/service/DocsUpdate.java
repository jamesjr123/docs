package org.atp25d.service;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.atp25d.model.Doctor;
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
    public void saveDoc(Doctor doc){
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
}
