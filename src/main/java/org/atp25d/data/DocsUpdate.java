package org.atp25d.data;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.atp25d.model.Doctor;
import org.jboss.as.quickstarts.kitchensink.model.Member;


@Stateless

public class DocsUpdate {
	
    @Inject
    private EntityManager em;

    @Inject
    private Event<Doctor> doctorEventSrc;    
    
    public Doctor getDocForUpdate(Doctor doc){
		return em.find(Doctor.class, doc.getDoctorNumber());    	
    }
    public void saveDoc(Doctor doc){
		em.merge(doc);    	
		doctorEventSrc.fire(doc);
    }
}
