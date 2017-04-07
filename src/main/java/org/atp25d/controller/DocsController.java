package org.atp25d.controller;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.data.DocsRepository;
import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.Location;
import org.atp25d.service.DocsUpdate;
import org.atp25d.util.FacesSession;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private List<DoctorNote> doctorNotes;
	
	
	@Inject
    private FacesSession facesSession;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private DocsRepository docsRepository;
	
	@Inject
	private DocsUpdate docsUpdate;
	
    @Produces
    @Named
    public List<DoctorNote> getDoctorNotes() {
        return doctorNotes;
    }	
	
	private Doctor newDoc;
	
	private Location newLoc;
	
	private DoctorNote newNote;
	
	public DoctorNote getNewNote() {
		return newNote;
	}

	public void setNewNote(DoctorNote newNote) {
		this.newNote = newNote;
	}

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
			initNewLoc();
			return "locationUpdate";
		}
		public String createDoc (){
			initNewDoc();
			return "doctorUpdate";
		}
		public String createNote (){
			initNewNote();
			return "noteUpdate";
		}				
		public String displayLoc (Location loc){
			newLoc = loc; // docsUpdate.getDocForUpdate(doc);		
			return "locationDisplay";			
		}
		public String listDocNotes (Doctor doc){
			newDoc = doc; // docsUpdate.getDocForUpdate(doc);		    
		    doctorNotes = docsRepository.findDocNotes(doc.getDoctorId());		    			    			
			return "doctorNotes";			
		}			
		public String selectLoc (Location loc){
			newDoc.setLocation(loc);  		
			return null;				
		}
		public String addLoc (Location loc){
			Doctor docCopy=newDoc;
			initNewDoc();
			newDoc.setCategory(docCopy.getCategory());
			newDoc.setDoctorId(docCopy.getDoctorId());
			newDoc.setFirstName(docCopy.getFirstName());						
			newDoc.setFirstName(docCopy.getFirstName());
			newDoc.setMiddleName(docCopy.getMiddleName());
			newDoc.setSurname(docCopy.getSurname());			
			newDoc.setLocation(loc);  		
			return null;				
		}			
		   public String saveLoc()  {
			   	newLoc.setUser(userEmail);	
			   	newLoc.setTime_Stamp(new Date());			   	
			    docsUpdate.saveLoc(newLoc);  
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Location record updated", "update");
				          facesContext.addMessage(null, m);
				  
					return "locations";
				  }
		   public String saveNote()  {
			   	newNote.setUser(userEmail);	
			   	newNote.setTime_Stamp(new Date());	
			   	newNote.setDoctorId(newDoc.getDoctorId());
			   	newNote.setDoctorNumber(newDoc.getDoctorNumber());			   	
			    docsUpdate.saveNote(newNote);  
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor note updated", "update");
				          facesContext.addMessage(null, m);
				 return listDocNotes(newDoc);
					//return "locations";
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
			    initNewLoc();
				return "locations";
			  }
	   public String cancelN()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Note update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewNote();
				return "doctorNotes";
			  } 	   	   
   private void initNewDoc(){
	   newDoc = new Doctor();
   }
   private void initNewNote(){
	   newNote = new DoctorNote();
   }      
   private void initNewLoc(){
	   newLoc = new Location();
   }
	public Location getNewLoc() {
		return newLoc;
	}

	public void setNewLoc(Location newLoc) {
		this.newLoc = newLoc;
	}
}
