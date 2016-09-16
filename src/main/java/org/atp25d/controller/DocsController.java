package org.atp25d.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.atp25d.util.FacesSession;

import java.io.Serializable;

@SessionScoped
@Named
public class DocsController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2728333040258701261L;

	@Inject
    private FacesSession facesSession;	
		
		
	
}
