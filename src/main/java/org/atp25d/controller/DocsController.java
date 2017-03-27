package org.atp25d.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.data.DocsRepository;
import org.atp25d.util.FacesSession;

import java.io.Serializable;

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
	private DocsRepository docsRepository;
	
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
		
		
	
}
