package org.atp25d.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.model.Doctor;
import org.atp25d.model.Location;



@RequestScoped
public class DocsListProducer {
	@Inject
    private DocsRepository docsRepository;

    private List<Doctor> doctors;
    private List<Location> locations;
		
    @Produces
    @Named
    public List<Doctor> getDoctors() {
        return doctors;
    }	

    @Produces
    @Named
    public List<Location> getLocations() {
        return locations;
    }	
        
    @PostConstruct
    public void init() {
    	retrieveAllDocs(); 
    	retrieveAllLocs();
    }
    public void retrieveAllDocs() {
        doctors = docsRepository.findAllDocs();       
    }
   
    public void retrieveAllLocs() {
        locations = docsRepository.findAllLocations();       
    }    
    
    public void onLocationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Location location) {
        retrieveAllLocs();
    }
    
    public void onDoctorListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Doctor doctor) {
        retrieveAllDocs();
    }
}
