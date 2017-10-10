package org.atp25d.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.atp25d.model.Doctor;
import org.atp25d.model.Reference_Data;


	@ManagedBean
	@ApplicationScoped
	public class ReferenceData {
	
		   @Inject
		    private EntityManager em;		
		
		public TimeZone getTimeZone() {
		    return TimeZone.getTimeZone(getRefTZ("EST"));
		}	
		
		public Map<String,String>  getRefList(String refType) {
			Map<String,String> list = new LinkedHashMap<String, String>();
			TypedQuery<Reference_Data> query = em.createNamedQuery("Reference_Data.findByListByRefType", Reference_Data.class);
			query.setParameter("refType", refType);
			List<Reference_Data> refs = query.getResultList();			
			for (Reference_Data ref: refs ) {
				list.put(ref.getCode(),ref.getValue() );
			}			
			return list;			
		}
		public Map<String,String>  getRefListR(String refType) {
			Map<String,String> list = new LinkedHashMap<String, String>();
			TypedQuery<Reference_Data> query = em.createNamedQuery("Reference_Data.findByListByRefType", Reference_Data.class);
			query.setParameter("refType", refType);
			List<Reference_Data> refs = query.getResultList();			
			for (Reference_Data ref: refs ) {
				list.put(ref.getValue(),ref.getCode() );
			}			
			return list;			
		}	
		public List<String>  getRefCodeList(String refType) {
			List<String> list = new ArrayList<String>();
			TypedQuery<Reference_Data> query = em.createNamedQuery("Reference_Data.findByListByRefType", Reference_Data.class);
			query.setParameter("refType", refType);
			List<Reference_Data> refs = query.getResultList();			
			for (Reference_Data ref: refs ) {
				list.add(ref.getCode() );
			}			
			return list;			
		}		
		public String  getRefTZ() {							
			return getRefTZ("");			
		}				
		public String  getRefTZ(String tp) {
			TypedQuery<Reference_Data> query = em.createNamedQuery("Reference_Data.findByListByRefType", Reference_Data.class);
			query.setParameter("refType", "TimeZone"+tp);
			List<Reference_Data> refs = query.getResultList();					
			return refs.iterator().next().getValue();			
		}		
		public Map<String,String>  getUserRefList(String refType, String userId) {
			Map<String,String> list = new LinkedHashMap<String, String>();
			TypedQuery<Reference_Data> query = em.createNamedQuery("Reference_Data.findByListByUserRefType", Reference_Data.class);
			query.setParameter("refType", refType);
			query.setParameter("userId", userId);
			List<Reference_Data> refs = query.getResultList();			
			for (Reference_Data ref: refs ) {
				list.put(ref.getCode(),ref.getValue() );
			}			
			return list;			
		}		

}
