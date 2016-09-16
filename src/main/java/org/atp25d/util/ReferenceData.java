package org.atp25d.util;

import java.util.TimeZone;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;


	@ManagedBean
	@ApplicationScoped
	public class ReferenceData {
		
		public TimeZone getTimeZone() {
		    return TimeZone.getDefault();
		}	
		
	

}
