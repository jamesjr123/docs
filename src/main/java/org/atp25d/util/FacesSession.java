package org.atp25d.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;



	@SessionScoped
	@Named
	public class FacesSession implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6656980001085654809L;

		/**
		 * 
		 */
		
		@Inject
	    private FacesContext facesContext;	
		
		
//		   public BaseUser getLoggedInUser(){
//			   BaseUser user = (BaseUser)facesContext.getExternalContext().getSessionMap().get("ESSUser");    		
//			   if (user==null){
//				user = (BaseUser) BaseUser.getDummyUser("Unknown", new NVPairList());
//			   }
//			return user;	
//		   }	
		
		   public Date getTimestamp(){
			   return new Date();
		   }
		   	
		   public String systemNodeText(){
		       String nodename = "";
			try {
				nodename = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       if (nodename.contains(".")){
		           nodename = nodename.substring(0,nodename.indexOf(".") + 1);
		       }	   
		     return nodename;
		   }

		   
			@PostConstruct	
			   public void initMe() {	    	
			   }	   
	}

	

