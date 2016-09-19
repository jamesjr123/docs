package org.atp25d.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the locations database table.
 * 
 */
@Entity
@Table(name="locations")
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l where l.locationNumber < 21")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int locationNumber;

	private String location;

	private String mailAddress1;

	private int mailPostcode;

	private String mailState;

	private String mailSuburb;

	private String user;

	//bi-directional many-to-one association to Doctor
	@OneToMany(mappedBy="location")
	private List<Doctor> doctors;

	public Location() {
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