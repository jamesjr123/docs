package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the doctor_notes database table.
 * 
 */
@Entity
@Table(name="doctor_notes")
@NamedQueries({
@NamedQuery(name="DoctorNote.findAll", query="SELECT d FROM DoctorNote d"),
@NamedQuery(name="DoctorNote.findByUserId", query="SELECT d FROM DoctorNote d where d.user = :userId"),
@NamedQuery(name="DoctorNote.findByDocId", query="SELECT d FROM DoctorNote d where d.doctorId = :doctorId"),
@NamedQuery(name="DoctorNote.findByByUserDate", query="SELECT d FROM DoctorNote d where d.user = :userId and d.time_Stamp >= :fromDate and  d.time_Stamp <= :toDate")
})
public class DoctorNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int noteId;

	private int doctorId;

	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;

	private String user;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="doctorNumber")
	private Doctor doctor;	
	
	
	public DoctorNote() {
	}

	public int getNoteId() {
		return this.noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public int getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getTime_Stamp() {
		return this.time_Stamp;
	}

	public void setTime_Stamp(Date time_Stamp) {
		this.time_Stamp = time_Stamp;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

}