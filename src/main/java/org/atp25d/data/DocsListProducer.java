package org.atp25d.data;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;

import java.io.Serializable;



@SessionScoped
@Named
public class DocsListProducer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -125349312297849472L;

	@Inject
    private DocsRepository docsRepository;

    private List<Doctor> doctors;
    private List<Doctor> doctorsLoc;    

	private List<Location> locations;
    private Location selectedLoc;
    private String specialist;
    
    @Produces
    @Named    
	public String getSpecialist() {
		return specialist;
	}
	public void setSpecialist(String specialist) {
		if (this.specialist!=null && !specialist.equals(this.specialist)) {
			this.specialist = specialist;
    		doctors = docsRepository.findAllDocs(specialist);
    		locations = docsRepository.findAllLocations(specialist);
		}
		else
		{
			this.specialist = specialist;
		}
	}
		
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
    @Produces
    @Named
    public List<Doctor> getDoctorsLoc() {
        return doctorsLoc;
    }	    
    @PostConstruct
    public void init() {
    	setSpecialist("Y");
    	retrieveAllDocs();
    	retrieveAllLocs();    
    }
    public void retrieveAllDocs() {
    	if (doctors==null) {
    		doctors = docsRepository.findAllDocs(specialist);
    	}
    }
    public void retrieveAllDocsLoc(Location loc) {
    	if (doctorsLoc==null) {
    		doctors = docsRepository.findAllDocsLoc(loc,specialist);
    	}
    }   
    public void retrieveAllLocs() {
    	if (locations==null) {
    		locations = docsRepository.findAllLocations(specialist);
    	}
    }      
    public void onLocationListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Location location) {
    	locations = docsRepository.findAllLocations(specialist);
    	doctors = docsRepository.findAllDocs(specialist);
    }
    
    public void onDoctorListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Doctor doctor) {
    	doctors = docsRepository.findAllDocs(specialist);
    	if (selectedLoc!=null && doctor.getLocation().getLocationNumber()==selectedLoc.getLocationNumber()) {
    		doctorsLoc = docsRepository.findAllDocsLoc(doctor.getLocation(),specialist);
    	}
    }
    @Produces
    @Named
	public Location getSelectedLoc() {
		return selectedLoc;
	}

    public void setDocsByLoc(Location loc){
    	setSelectedLoc(loc);
		setDoctorsLoc(docsRepository.findAllDocsLoc(loc,specialist));    	
    }
    
	public void setSelectedLoc(Location selectedLoc) {
		this.selectedLoc = selectedLoc;
	}
    public void setDoctorsLoc(List<Doctor> doctorsLoc) {
		this.doctorsLoc = doctorsLoc;
	}
    public void specialistKeyEvent()
    {
    	doctors = docsRepository.findAllDocs(specialist);    	
    }
}
