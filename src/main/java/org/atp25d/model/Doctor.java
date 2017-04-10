package org.atp25d.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the doctors database table.
 * 
 */
@Entity
@Table(name="doctors")
@NamedQueries({
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d order by d.surname"),
@NamedQuery(name="Doctor.findLastDocId", query="select max(d.doctorId) FROM Doctor d") ,
@NamedQuery(name="Doctor.findById", query="SELECT d FROM Doctor d where d.doctorNumber = :doctorNumber")
})
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int doctorNumber;

	private int doctorId;

	private String firstName;

	private String middleName;

	private String surname;

	private String user;

	private String title;

	private String quals;
	
	private String emailAddress;
	
	@Transient
	private String fullName;	
	
	@Transient
	private String displayName;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;	
	
	private String category;
	
	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="LocationNumber")
	private Location location;

	
	
	public Doctor() {
	}
	public String getDisplayName() {
		return firstName+" "+surname;
	}

	public String getQuals() {
		return quals;
	}
	public void setQuals(String quals) {
		this.quals = quals;
	}
	public String getTitle() {
		return title;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFullName() {
		return firstName+ " "+middleName+" "+surname;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}	
	
	public Date getTime_Stamp() {
		return time_Stamp;
	}

	public void setTime_Stamp(Date time_Stamp) {
		this.time_Stamp = time_Stamp;
	}
	
	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
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