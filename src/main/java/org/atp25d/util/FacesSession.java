package org.atp25d.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;



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
		

	    private Map<String, String> themes;
	    private String theme;		
		
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

		   public String getTheme() {
		       //if (!checkedDBForTheme) getThemeFromDatabase();
			   if(theme == null || theme.length() == 0) theme = "start";
		       return theme;
		   }

		   public void setTheme(String theme) {
		       this.theme = theme;
		       // save to a cookie
		       java.util.Map<java.lang.String,java.lang.Object> properties = new HashMap<java.lang.String,java.lang.Object>();
		       properties.put("maxAge", Integer.valueOf(365*24*60*60)  );
		       properties.put("path", "/docs");
		       facesContext.getExternalContext().addResponseCookie("docjrjsftheme", theme, properties);
		   }
		   
		   public Map<String, String> getThemes() {
		       return themes;
		   }		   
		   
		   
			@PostConstruct	
			   public void initMe() {	    
				
				Map<String, Object> cookies = facesContext.getExternalContext().getRequestCookieMap();
				Cookie cookie = (Cookie)cookies.get("docjrjsftheme");
				if (cookie != null) theme = cookie.getValue();
				else theme = "cupertino";
		   	 	
		        themes = new TreeMap<>();
		        themes.put("Afterdark", "afterdark");
		        themes.put("Afternoon", "afternoon");
		        themes.put("Afterwork", "afterwork");
		        themes.put("Aristo", "aristo");
		        themes.put("Black-Tie", "black-tie");
		        themes.put("Blitzer", "blitzer");
		        themes.put("Bluesky", "bluesky");
		        themes.put("Bootstrap", "bootstrap");
		        themes.put("Casablanca", "casablanca");
		        themes.put("Cupertino", "cupertino");
		        themes.put("Cruze", "cruze");
		        themes.put("Dark-Hive", "dark-hive");
		        themes.put("Dot-Luv", "dot-luv");
		        themes.put("Eggplant", "eggplant");
		        themes.put("Excite-Bike", "excite-bike");
		        themes.put("Flick", "flick");
		        themes.put("Glass-X", "glass-x");
		        themes.put("Hot-Sneaks", "hot-sneaks");
		        themes.put("Home", "home");
		        themes.put("Humanity", "humanity");
		        themes.put("Le-Frog", "le-frog");
		        themes.put("Midnight", "midnight");
		        themes.put("Mint-Choc", "mint-choc");
		        themes.put("Overcast", "overcast");
		        themes.put("Pepper-Grinder", "pepper-grinder");
		        themes.put("Redmond", "redmond");
		        themes.put("Rocket", "rocket");
		        themes.put("Sam", "sam");
		        themes.put("Smoothness", "smoothness");
		        themes.put("South-Street", "south-street");
		        themes.put("Start", "start");
		        themes.put("Sunny", "sunny");
		        themes.put("Swanky-Purse", "swanky-purse");
		        themes.put("Trontastic", "trontastic");
		        themes.put("UI-Darkness", "ui-darkness");
		        themes.put("UI-Lightness", "ui-lightness");
		        themes.put("Vader", "vader");				
			   }	   
	}

	

