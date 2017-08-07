package org.atp25d.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
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
import java.io.OutputStreamWriter;
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
public class RefDataController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4032916552531722494L;


	/**
	 * 
	 */
	private String todoProject;
	private String todoCode;
	private String todoToken;
	private String todoistClientId;
	private String todoResponse;
    private List<Reference_Data> reference_Data;    
    private String genCode; 
    private List<Reference_Data> project_Data;
    
	@Inject
    private FacesSession facesSession;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private DocsRepository docsRepository;
	
	@Inject
	private DocsUpdate docsUpdate;
	
	@Inject
	private DocsController  docsController;

	@Inject
	ReferenceData referenceData;
	

    @Produces
    @Named	
	public List<Reference_Data> getProject_Data() {
		return project_Data;
	}
    @Produces
    @Named	
	public List<Reference_Data> getReference_Data() {
		return reference_Data;
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
			return "settings";				
		}
		public String deleteRefRow(Reference_Data row){
			docsUpdate.deleteRefData(row);
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Row deleted", "");
	          facesContext.addMessage(null, m);				
			loadRefData();
			loadProjectData();
			return "settings";				
		}					
		public String addTodoist(){
			Reference_Data cpy=new Reference_Data();			
			cpy.setCode(getTodoProject());
			cpy.setValue(getTodoCode());
			cpy.setUserId(docsController.getUserEmail());
			cpy.setRefType("TodoistProject");
			docsUpdate.saveRefData(cpy);
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Project added", "");
	          facesContext.addMessage(null, m);				
	  		loadProjectData();
			return "settings";				
		}
		public String saveToken(){
			UserProfile usr = docsUpdate.getUserForUpdate(docsController.getUserEmail());
			usr.setTodoistToken(todoToken);
			docsUpdate.saveUserProfile(usr);
		 	  FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "token saved", "");
	          facesContext.addMessage(null, m);				
			return "settings";				
		}		
	@PostConstruct
	public void init() {
		loadRefData();
		loadProjectData();
		setTodoistClientId("fda1fa78f62d4245a46e7eb9451e06e5");
		setTodoToken(docsUpdate.getUserForUpdate(docsController.getUserEmail()).getTodoistToken());
	}	

	private void loadRefData(){  
	    reference_Data=docsRepository.getRefData();
	}
	private void loadProjectData(){  
	    project_Data=docsRepository.getUserRefData(docsController.getUserEmail(), "TodoistProject");
	}
	public void setProject_Data(List<Reference_Data> project_Data) {
		this.project_Data = project_Data;
	}
	public String getTodoProject() {
		return todoProject;
	}
	public void setTodoProject(String todoProject) {
		this.todoProject = todoProject;
	}
	public String getTodoCode() {
		return todoCode;
	}
	public void setTodoCode(String todoCode) {
		this.todoCode = todoCode;
	}
	public String getTodoToken() {
		return todoToken;
	}
	public void setTodoToken(String todoToken) {
		this.todoToken = todoToken;
	}
	public String getTodoistClientId() {
		return todoistClientId;
	}
	public void setTodoistClientId(String todoistClientId) {
		this.todoistClientId = todoistClientId;
	}	
	public String todoAuth() throws IOException {
			if (getGenCode().equals("")) {
				facesContext.getExternalContext().redirect("settings.jsf");
				return "settings";
			}
			setTodoResponse("");
	    	String urlString = "https://todoist.com/oauth/access_token";
		     String query="client_id=fda1fa78f62d4245a46e7eb9451e06e5&client_secret=2c8682895878406a9bf74c4dafa846e1&code="+getGenCode();
			URL url;
			try {
				url = new URL(urlString+"?"+query);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("POST");    	    	            		   
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");			            
			connection.setDoOutput(true);

		    
				connection.setRequestMethod("POST");    	    	            		   			            
				connection.setDoOutput(true);

				  OutputStreamWriter out = new OutputStreamWriter(
				      connection.getOutputStream());
				
				System.out.println("Resp Code:"+connection .getResponseCode());
				setTodoResponse(connection.getResponseMessage());
				System.out.println("Resp Message:"+ getTodoResponse());
				out.close();
				DataInputStream input = new DataInputStream( connection.getInputStream() ); 
				StringBuffer returnCode = new StringBuffer();
				for( int c = input.read(); c != -1; c = input.read() ) {
					returnCode.append((char)c); 
				}				
			System.out.println(query);
			System.out.println("Resp Code:"+connection .getResponseCode());
			setTodoResponse(returnCode.toString());
			System.out.println("Resp Message:"+ getTodoResponse());
			
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
			facesContext.getExternalContext().redirect("settings.jsf");
			return "settings";
		}
	public String getTodoResponse() {
		return todoResponse;
	}
	public void setTodoResponse(String todoResponse) {
		this.todoResponse = todoResponse;
	}
	public String getGenCode() {
		if (genCode==null) return ""; 
		return genCode;
	}
	public void setGenCode(String genCode) {
		this.genCode = genCode;
	}
	
}
