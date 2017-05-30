package org.atp25d.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;

import org.atp25d.data.DocsListProducer;
import org.atp25d.data.DocsRepository;
import org.atp25d.model.Doctor;
import org.atp25d.model.DoctorNote;
import org.atp25d.model.DoctorTarget;
import org.atp25d.model.Location;
import org.atp25d.model.LocationNote;
import org.atp25d.model.Reference_Data;
import org.atp25d.model.UserProfile;
import org.atp25d.service.DocsUpdate;
import org.atp25d.util.FacesSession;
import org.atp25d.util.ReferenceData;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.event.data.PageEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;

@SessionScoped
@Named
public class DocsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728333040258701261L;
	private String userName;
	private String userEmail;
	private UserProfile userProfile;
	private boolean loggedIn;
	private boolean docsReadAccess;
	private boolean docsUpdateAccess;
	private boolean fromDoc;
    private List<DoctorNote> doctorNotes;
    private List<DoctorNote> myDocNotes;
    private List<LocationNote> myLocNotes;
    private List<DoctorTarget> targetDoctors;

    private List<Reference_Data> reference_Data;    
    
    private List<LocationNote> locationNotes;    
	private String docNameFilter;
	private String docsList;
    private List<Doctor> filteredDoctors;
    private List<DoctorTarget> filteredTargetDoctors;
	private List<Location> filteredLocations;
	private int locsPageNo;
	private int docsPageNo;
	private int docsTarPageNo;	
    private Date notesDateTo;
    private Date notesDateFrom;
    private Date notesLocDateTo;
    private Date notesLocDateFrom;     
    private String noteSubject;
    private String noteBody;
    private List<String> selectedTargets;
	
	@Inject
    private FacesSession facesSession;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private DocsRepository docsRepository;
	
	@Inject
	private DocsUpdate docsUpdate;
	
	@Inject
	private DocsListProducer  docsListProducer;

	@Inject
	ReferenceData referenceData;
	
    @Produces
    @Named
    public List<DoctorNote> getDoctorNotes() {
        return doctorNotes;
    }	
    @Produces
    @Named	
	public List<LocationNote> getLocationNotes() {
		return locationNotes;
	}
    @Produces
    @Named	
	public List<Reference_Data> getReference_Data() {
		return reference_Data;
	}
	private Doctor newDoc;
	
	private Location newLoc;
	
	private DoctorNote newDocNote;
	
	private LocationNote newLocNote;
	
	public DoctorNote getNewDocNote() {
		return newDocNote;
	}

	public LocationNote getNewLocNote() {
		return newLocNote;
	}

	public void setNewLocNote(LocationNote newLocNote) {
		this.newLocNote = newLocNote;
	}

	public void setNewNote(DoctorNote newDocNote) {
		this.newDocNote = newDocNote;
	}

	public Doctor getNewDoc() {
		return newDoc;
	}

	public int getLocsPageNo() {
		return locsPageNo;
	}
	public void setLocsPageNo(int locsPageNo) {
		this.locsPageNo = locsPageNo;
	}
	public void setNewDoc(Doctor newDoc) {
		this.newDoc = newDoc;
	}

	public List<Location> getFilteredLocations() {
		return filteredLocations;
	}
	public void setFilteredLocations(List<Location> filteredLocations) {
		this.filteredLocations = filteredLocations;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDocsList() {
		if (docsList==null) {
			setDocsList("index");
		}
		return docsList;
	}
	public void setDocsList(String docsList) {
		this.docsList = docsList;
	}
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public List<Doctor> getFilteredDoctors() {
		return filteredDoctors;
	}
	public void setFilteredDoctors(List<Doctor> filteredDoctors) {
		this.filteredDoctors = filteredDoctors;
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
	public String updateDocNote (DoctorNote docNote){
		newDocNote = docsUpdate.getDocNoteForUpdate(docNote);
		return "noteUpdate";		
	}
	public String updateLocNote (LocationNote locNote){
		newLocNote = docsUpdate.getLocNoteForUpdate(locNote);
		return "locNoteUpdate";		
	}		
	public String displayDoc (Doctor doc){
		newDoc = doc; // docsUpdate.getDocForUpdate(doc);		
		return "doctorDisplay";		
	}	
	public String displayDocNote (DoctorNote docNote){
		newDocNote = docNote; // docsUpdate.getDocForUpdate(doc);		
		return "noteDisplay";		
	}
	public String displayLocNote (LocationNote locNote){
		newLocNote = locNote;  		
		return "locNoteDisplay";		
	}		
	   public String saveDoc()  {
		   if (newDoc.getLocation()==null){
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter an address", "error");
		          facesContext.addMessage(null, m);			   		   
			   return "doctorUpdate";
		   }
		   	newDoc.setUser(userEmail);
		   	newDoc.setTime_Stamp(new Date());
		   	newDoc.setFirstName(newDoc.getFirstName().trim());
	    	if (newDoc.getDoctorNumber() == 0 && docsUpdate.docExists(newDoc)) {
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already exists", "error");
		          facesContext.addMessage(null, m);
				   return "doctorUpdate";
	    	}
		    docsUpdate.saveDoc(newDoc);  
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor record updated", "update");
			          facesContext.addMessage(null, m);
			  
				return getDocsList();
			  }
		public String updateLoc (Location loc){
			fromDoc=false;
			newLoc = docsUpdate.getLocForUpdate(loc);
			return "locationUpdate";			
		}
		public String listDocsByLoc(Location loc){
			docsListProducer.setSelectedLoc(loc);
			docsListProducer.setDoctorsLoc(docsRepository.findAllDocsLoc(loc));
			setDocsList("doctorsLoc");
			return "doctorsLoc";			
		}				
		public String listDoctors(){
			setDocsList("index");
			return "index";			
		}						
		public String createLoc (){
			initNewLoc();
			fromDoc=false;
			return "locationUpdate";
		}
		public String createLocFromDoc (){
			initNewLoc();
			fromDoc=true;
			return "locationUpdate";
		}		
		public String createDoc (){
			initNewDoc();
			return "doctorUpdate";
		}
		public String createDoc (Location loc){
			initNewDoc();
			newDoc.setLocation(loc);
			return "doctorUpdate";
		}		
		public String createNote (){
			initNewDocNote();
			return "noteUpdate";
		}
		public String createLocNote (){
			initNewLocNote();
			return "locNoteUpdate";
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
		public String listLocNotes (Location loc){
			newLoc = loc; 		    
		    locationNotes = docsRepository.findLocNotes(loc.getLocationNumber());		    			    			
			return "locationNotes";			
		}
		public String listMyDocNotes (String user){
		    myDocNotes = docsRepository.findMyDocNotes(user);		    			    			
			return "myDocNotes";			
		}		
		public String listMyLocNotes (String user){
		    myLocNotes = docsRepository.findMyLocNotes(user);		    			    			
			return "myLocNotes";			
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
			newDoc.setTitle(docCopy.getTitle());			
			newDoc.setQuals(docCopy.getQuals());
			newDoc.setEmailAddress(docCopy.getEmailAddress());
			newDoc.setPhoneNumber(docCopy.getPhoneNumber());
			newDoc.setLocation(loc);  		
			return null;				
		}
	    public void onRefRowEdit(RowEditEvent event) {
	    	Reference_Data row = (Reference_Data) event.getObject();
	    	docsUpdate.saveRefData(row);
	        FacesMessage msg = new FacesMessage("Record Updated", "Record Updated");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }
	     
	    public void onRefRowCancel(RowEditEvent event) {
	        FacesMessage msg = new FacesMessage("Edit Cancelled", "Edit Cancelled");
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }		
		public String copyRefRow(Reference_Data row){
			Reference_Data cpy=new Reference_Data();			
			cpy.setCode(row.getCode());
			cpy.setValue(row.getValue());
			cpy.setCompId(row.getCompId());
			cpy.setUserId(row.getUserId());
			cpy.setRefType(row.getRefType());
			docsUpdate.saveRefData(cpy);
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row copied", "");
	          facesContext.addMessage(null, m);				
			loadRefData();
			return "referenceData";				
		}
		public String deleteRefRow(Reference_Data row){
			docsUpdate.deleteRefData(row);
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row deleted", "");
	          facesContext.addMessage(null, m);				
			loadRefData();
			return "referenceData";				
		}					
		   public String saveLoc()  {
			   	newLoc.setUser(userEmail);	
			   	newLoc.setTime_Stamp(new Date());
			   	newLoc.setLocation(newLoc.getLocation().trim());
		    	if (newLoc.getLocationNumber() == 0 && docsUpdate.locExists(newLoc)) {
				 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Record already exists", "error");
			          facesContext.addMessage(null, m);
					   return "locationUpdate";
		    	}			   	
			    docsUpdate.saveLoc(newLoc);  
			    if (fromDoc)
			    {
				 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "New Location added", "update");
			          facesContext.addMessage(null, m);			    	
			    	return "doctorUpdate";
			    }
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Location record updated", "update");
				          facesContext.addMessage(null, m);				  
					return "locations";
				  }
	   
		   public String saveDocNote()  {
			   	newDocNote.setUser(userEmail);	
			   	newDocNote.setTime_Stamp(new Date());	
			   	newDocNote.setDoctorId(newDoc.getDoctorId());
			//   	newDocNote.setDoctorNumber(newDoc.getDoctorNumber());
			   	newDocNote.setDoctor(newDoc);
			    docsUpdate.saveDocNote(newDocNote);  
			   	if (!getUserProfile().getTodoistToken().equals("") && newDocNote.getFollowUp() !=null)
			   	{
			   		writeTodoTask(getUserProfile().getTodoistToken(), "DocNote");
			   	}			    
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor note updated", "update");
				          facesContext.addMessage(null, m);
				 return listDocNotes(newDoc);
					//return "locations";
				  }
		   public String saveLocNote()  {
			   	newLocNote.setUser(userEmail);	
			   	newLocNote.setTime_Stamp(new Date());	
			   	newLocNote.setNoteId(newLocNote.getNoteId());
			   	newLocNote.setLocation(newLoc);
			    docsUpdate.saveLocNote(newLocNote);
			   	if (!getUserProfile().getTodoistToken().equals("") && newLocNote.getFollowUp() !=null)
			   	{
			   		writeTodoTask(getUserProfile().getTodoistToken(), "LocNote");
			   	}				    
			 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor note updated", "update");
				          facesContext.addMessage(null, m);
				 return listLocNotes(newLoc);
					//return "locations";
				  }	  			   
	   public String cancel()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Doctor update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewDoc();
			    return getDocsList();
			  }
	   public String backDoc()  {		 	  
			    return getDocsList();
			  }	   
	   public String cancelL()  {
		    if (fromDoc)
		    {
		    	return "doctorUpdate";
		    }		   
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Location update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewLoc();
				return "locations";
			  }
	   public String cancelN()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Note update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewDocNote();
				return "doctorNotes";
			  }
	   public String cancelLN()  {
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Note update cancelled", "update cancelled");
			          facesContext.addMessage(null, m);
			    initNewDocNote();
				return "locationNotes";
			  }
	   public String filterMyDocNotes()  {
		   myDocNotes = docsRepository.findMyDocNotesByDate(userEmail, notesDateFrom, notesDateTo);
				return "myDocNotes";
			  }
	   public String filterMyLocNotes()  {
		   myLocNotes = docsRepository.findMyLocNotesByDate(userEmail, notesLocDateFrom, notesLocDateTo);
				return "myLocNotes";
			  }	   
	   public String filterTargets()  {
		   if (selectedTargets.isEmpty()){
			   targetDoctors=docsRepository.getAllTargetDocs();
		   }
		   else 
		   {
			   targetDoctors=docsRepository.getTargetDocs(selectedTargets);
		   }
		   return "targetDoctors";
	   }	   	   
	   public void initNewDoc(){
	   newDoc = new Doctor();
   }
   public void initNewDocNote(){
	   newDocNote = new DoctorNote();
   }
   public void initNewLocNote(){
	   newLocNote = new LocationNote();
   }         
   public void initNewLoc(){
	   newLoc = new Location();
   }
   public void pageListenerDocs(PageEvent event) {
	   setDocsPageNo(event.getPage());	   
	 }
   public void pageListenerTarDocs(PageEvent event) {
	   setDocsTarPageNo(event.getPage());	   
	 }   
   public void pageListenerLocs(PageEvent event) {
	   setLocsPageNo(event.getPage());	   
	 }         
   public void filterListenerTarDocs(FilterEvent filterEvent) {	   
	   filterListener(filterEvent,"docs:docTarTable");
	   setDocTarPageNo();
   }
   public void filterListenerDocs(FilterEvent filterEvent) {	   
	   filterListener(filterEvent,"docs:docTable");
	   setDocPageNo();
   }   
   public void filterListenerLocs(FilterEvent filterEvent) {
	   filterListener(filterEvent,"docs:locTable");
	   setLocPageNo();
   }   
   public void filterListener(FilterEvent filterEvent, String table) {
	   Map<String, Object> ff = filterEvent.getFilters();
	   DataTable s = (DataTable) filterEvent.getSource();	   
		Map<String, Object> session = facesContext.getExternalContext().getSessionMap();
		for (Entry<String, Object>  row : ff.entrySet()){
			String columnId = row.getKey();
			columnId =columnId.replace('.', '_'); // naming convention for filter column id sub fields
			session.put(s.getClientId()+":"+columnId, row.getValue());	  									
		}
		if (ff.isEmpty()) {						
			for (Entry<String, Object>  row : session.entrySet()){
				if (row.getKey().startsWith(table)){
					session.put(row.getKey(),"");	  														
				}
			}
		}		
	 }   
   public void setDocPageNo() {
	   RequestContext.getCurrentInstance().execute("PF('docsTable').getPaginator().setPage("+getDocsPageNo()+")");
   }   
   public void setDocTarPageNo() {
	   RequestContext.getCurrentInstance().execute("PF('docsTargetTable').getPaginator().setPage("+getDocsTarPageNo()+")");
   }   
   public void setLocPageNo() {
	   RequestContext.getCurrentInstance().execute("PF('locsTable').getPaginator().setPage("+getLocsPageNo()+")");
   }   
	public Location getNewLoc() {
		return newLoc;
	}
	@PostConstruct
	public void init() {
		notesDateFrom = new Date();
		notesLocDateTo = new Date();
		notesLocDateFrom = new Date();		
		notesDateTo = new Date();
		loadRefData();
		//campaigns = referenceData.getRefList("DoctorCampaign");
	}	
	public void setNewLoc(Location newLoc) {
		this.newLoc = newLoc;
	}
	public UserProfile getUserProfile() {
		if (userProfile==null ) {
			setUserProfile(docsRepository.findUserProfile(getUserEmail()));
		}
		return userProfile;
	}
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	public String getDocNameFilter() {
		return docNameFilter;
	}
	public void setDocNameFilter(String docNameFilter) {
		this.docNameFilter = docNameFilter;
	}
	public int getDocsPageNo() {
		return docsPageNo;
	}
	public void setDocsPageNo(int docsPageNo) {
		this.docsPageNo = docsPageNo;
	}
    @Produces
    @Named	
	public List<DoctorNote> getMyDocNotes() {
		return myDocNotes;
	}
	public void setMyDocNotes(List<DoctorNote> myDocNotes) {
		this.myDocNotes = myDocNotes;
	}
	public Date getNotesDateTo() {
		return notesDateTo;
	}
	public void setNotesDateTo(Date notesDateTo) {
		this.notesDateTo = notesDateTo;
	}
	public Date getNotesDateFrom() {
		return notesDateFrom;
	}
	public void setNotesDateFrom(Date notesDateFrom) {
		this.notesDateFrom = notesDateFrom;
	}
	public String getNoteSubject() {
		return noteSubject;
	}
	public void setNoteSubject(String noteSubject) {
		this.noteSubject = noteSubject;
	}
	public String getNoteBody() {
		return noteBody;
	}
	public void setNoteBody(String noteBody) {
		this.noteBody = noteBody;
	}
	public String formatNoteSubject(String subject, Doctor doc){  
	    return "Dear "+ doc.getTitle()+" "+doc.getDisplayName()+" "+subject;
	}
	private void loadRefData(){  
	    reference_Data=docsRepository.getRefData();
	}	
	private void writeTodoTask(String token, String fupType){
    	String urlString = "https://todoist.com/API/v7/sync";
		URL url;
		try {
			url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("POST");    	    	            		   
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");			            
		connection.setDoOutput(true);
		DataOutputStream output = new DataOutputStream(connection.getOutputStream());
		 UUID uuid = UUID.randomUUID();
	     String uuid1 = uuid.toString();		
		 uuid = UUID.randomUUID();
	     String uuid2 = uuid.toString();
	     SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	     dateFmt.setTimeZone(TimeZone.getTimeZone(referenceData.getRefTZ()));
	     String ddat;
	     String cdat;
	     String projectId;
	     String task;
	     String query;
	    if (fupType.equals("DocNote")) {
		  synchronized (dateFmt){
		        ddat= dateFmt.format(newDocNote.getFollowUp());
		        cdat= dateFmt.format(newDocNote.getTime_Stamp());
		      }	    	
		    task = newDocNote.getDoctor().getFullName()+" - "+newDocNote.getDoctor().getPhoneNumber()+"  "+newDocNote.getDoctor().getMobileNumber()+" - "+newDocNote.getDoctor().getLocation().getDisplayName()+ " - "+newDocNote.getNotes();
		    projectId = newDocNote.getProjectId();		    
	    }
	    else
	    {
		  synchronized (dateFmt){
		        ddat= dateFmt.format(newLocNote.getFollowUp());
		        cdat= dateFmt.format(newLocNote.getTime_Stamp());
		      }	    	
		    task = newLocNote.getLocation().getDisplayName()+" - "+newLocNote.getLocation().getPhoneNumber()+"  "+" - "+newLocNote.getNotes();
		    projectId = newLocNote.getProjectId();				    	
	    }
	    query="token="+token+"&commands=[{\"type\": \"item_add\", \"temp_id\": \""+uuid1+"\", \"uuid\": \""+uuid2+"\", \"args\": {\"content\": \""+task+"\", \"project_id\": \""+projectId+"\",\"due_date_utc\":\""+ddat+"\",\"date_string\":\""+cdat+"\"}}]";
		output.writeBytes(query);		
		output.close();	
		DataInputStream input = new DataInputStream( connection.getInputStream() ); 


		StringBuffer returnCode = new StringBuffer();
		for( int c = input.read(); c != -1; c = input.read() ) {
			returnCode.append((char)c); 
		}
		System.out.println("Message:"+returnCode.toString()); 

		input.close();
		System.out.println(query);
		System.out.println("Resp Code:"+connection .getResponseCode()); 
		System.out.println("Resp Message:"+ connection.getResponseMessage());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	public List<String> getSelectedTargets() {
		return selectedTargets;
	}
	public void setSelectedTargets(List<String> selectedTargets) {
		this.selectedTargets = selectedTargets;
	}
	public int getDocsTarPageNo() {
		return docsTarPageNo;
	}
	public void setDocsTarPageNo(int docsTarPageNo) {
		this.docsTarPageNo = docsTarPageNo;
	}
    @Produces
    @Named		
	public List<DoctorTarget> getTargetDoctors() {
    	if (targetDoctors==null){
    		targetDoctors=docsRepository.getAllTargetDocs();
    	}
		return targetDoctors;
	}
	public void setTargetDoctors(List<DoctorTarget> targetDoctors) {
		this.targetDoctors = targetDoctors;
	}
	public List<DoctorTarget> getFilteredTargetDoctors() {
		return filteredTargetDoctors;
	}
	public void setFilteredTargetDoctors(List<DoctorTarget> filteredTargetDoctors) {
		this.filteredTargetDoctors = filteredTargetDoctors;
	}
   @Produces
    @Named		
	public List<LocationNote> getMyLocNotes() {
		return myLocNotes;
	}
	public void setMyLocNotes(List<LocationNote> myLocNotes) {
		this.myLocNotes = myLocNotes;
	}
	public Date getNotesLocDateTo() {
		return notesLocDateTo;
	}
	public void setNotesLocDateTo(Date notesLocDateTo) {
		this.notesLocDateTo = notesLocDateTo;
	}
	public Date getNotesLocDateFrom() {
		return notesLocDateFrom;
	}
	public void setNotesLocDateFrom(Date notesLocDateFrom) {
		this.notesLocDateFrom = notesLocDateFrom;
	}	
}
