package org.atp25d.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the doctors database table.
 * 
 */
@Entity
@Table(name="doctors")
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d where d.doctorNumber < 21")
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int doctorNumber;

	private String firstName;

	private String middleName;

	private String surname;

	private String user;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="LocationNumber")
	private Location location;

	public Doctor() {
	}

	public int getDoctorNumber() {
		return this.doctorNumber;
	}

	public void setDoctorNumber(int doctorNumber) {
		this.doctorNumber = doctorNumber;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}