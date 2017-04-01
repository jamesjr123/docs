package org.atp25d.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.data.DocsRepository;
import org.atp25d.model.Doctor;
import org.atp25d.model.Location;
import org.atp25d.service.DocsUpdate;
import org.atp25d.util.FacesSession;

import java.io.Serializable;
import java.util.Date;

@SessionScoped
@Named
public class DocsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728333040258701261L;
	private String userName;
	private String userEmail;
	private boolean loggedIn;
	private boolean docsReadAccess;
	private boolean docsUpdateAccess;
	
	
	@Inject
    private FacesSession facesSession;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private DocsRepository docsRepository;
	
	@Inject
	private DocsUpdate docsUpdate;
	

	private Doctor newDoc;
	
	private Location newLoc;
	
	public Doctor getNewDoc() {
		return newDoc;
	}

	public void setNewDoc(Doctor newDoc) {
		this.newDoc = newDoc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		if ( this.loggedIn != loggedIn) {
			setDocsReadAccess(docsRepository.hasReadAccess(getUserEmail()));
			setDocsUpdateAccess(docsRepository.hasTaskAccess(getUserEmail(), "Update"));
		}
		this.loggedIn = loggedIn;
	}

	public boolean isDocsReadAccess() {
		return docsReadAccess;
	}

	public void setDocsReadAccess(boolean docsReadAccess) {
		this.docsReadAccess = docsReadAccess;
	}

	public boolean isDocsUpdateAccess() {
		return docsUpdateAccess;
	}

	public void setDocsUpdateAccess(boolean docsUpdateAccess) {
		this.docsUpdateAccess = docsUpdateAccess;
	}	
	public String updateDoc (Doctor doc){
		newDoc = docsUpdate.getDocForUpdate(doc);
		return "doctorUpdate";
		
	}
	public String displayDoc (Doctor doc){
		newDoc = doc; // docsUpdate.getDocForUpdate(doc);		
		return "doctorDisplay";
		
	}	
	   public String saveDoc()  {
		   	newDoc.setUser(userEmail);
		   	newDoc.setTime_Stamp(new Date());
		    docsUpdate.saveDoc(newDoc);  
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor record updated", "update");
			          facesContext.addMessage(null, m);
			  
				return "index";
			  }
		public String updateLoc (Location loc){
			newLoc = docsUpdate.getLocForUpdate(loc);
			return "locationUpdate";			
		}
		public String createLoc (){
			newLoc = new Location();;
			return "locationUpdate";
		}
		
		public String displayLoc (Location loc){
			newLoc = loc; // docsUpdate.getDocForUpdate(doc);		
			return "locationDisplay";
			
		}	
		   public String saveLoc()  {
			   	newLoc.setUser(userEmail);	
			   	newLoc.setTime_Stamp(new Date());			   	
			    docsUpdate.saveLoc(newLoc);  
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Location record updated", "update");
				          facesContext.addMessage(null, m);
				  
					return "locations";
				  }	   
	   public String cancel()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewDoc();
				return "index";
			  }
	   public String cancelL()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Location update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewDoc();
				return "locations";
			  } 	   
	   public void initNewDoc(){
		   newDoc = new Doctor();
	   }

	public Location getNewLoc() {
		return newLoc;
	}

	public void setNewLoc(Location newLoc) {
		this.newLoc = newLoc;
	}
}
