package org.atp25d.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.atp25d.model.Doctor;


@Stateless

public class DocsUpdate {
	
    @Inject
    private EntityManager em;
    
    public Doctor getDocForUpdate(Doctor doc){
		return em.find(Doctor.class, doc.getDoctorNumber());    	
    }
    public void saveDoc(Doctor doc){
		em.merge(doc);    	
    }
}
