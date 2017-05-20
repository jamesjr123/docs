package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the locations database table.
 * 
 */
@Entity
@Table(name="locations")
@NamedQueries({
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l"),
@NamedQuery(name="Location.findById", query="SELECT l FROM Location l where l.locationNumber = :locationNumber")
})
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int locationNumber;

	private String location;

	private String mailAddress1;

	private int mailPostcode;

	private String mailState;

	private String mailSuburb;

	private String emailAddress;
	
	private String phoneNumber;

	private String faxNumber;
	
	private String user;

	@Transient
	private String displayName;	
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;	
		
	//bi-directional many-to-one association to Doctor
	@OneToMany(mappedBy="location")
	private List<Doctor> doctors;

	public Location() {
	}

	public Date getTime_Stamp() {
		return time_Stamp;
	}

	public void setTime_Stamp(Date time_Stamp) {
		this.time_Stamp = time_Stamp;
	}	
	
	public String getDisplayName() {
		return location+ " "+mailAddress1+" "+mailSuburb+" "+mailPostcode+" "+mailState;
		
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getLocationNumber() {
		return this.locationNumber;
	}

	public void setLocationNumber(int locationNumber) {
		this.locationNumber = locationNumber;
	}

	public String getLocation() {
		return this.location;
	}

	public String getPhoneNumber() {
		if (phoneNumber==null) {
			setPhoneNumber("");
		}
		return phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMailAddress1() {
		return this.mailAddress1;
	}

	public void setMailAddress1(String mailAddress1) {
		this.mailAddress1 = mailAddress1;
	}

	public int getMailPostcode() {
		return this.mailPostcode;
	}

	public void setMailPostcode(int mailPostcode) {
		this.mailPostcode = mailPostcode;
	}

	public String getMailState() {
		return this.mailState;
	}

	public void setMailState(String mailState) {
		this.mailState = mailState;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMailSuburb() {
		return this.mailSuburb;
	}

	public void setMailSuburb(String mailSuburb) {
		this.mailSuburb = mailSuburb;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Doctor addDoctor(Doctor doctor) {
		getDoctors().add(doctor);
		doctor.setLocation(this);

		return doctor;
	}

	public Doctor removeDoctor(Doctor doctor) {
		getDoctors().remove(doctor);
		doctor.setLocation(null);

		return doctor;
	}

}