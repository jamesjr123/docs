package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the location_notes database table.
 * 
 */
@Entity
@Table(name="location_notes")
@NamedQueries({
@NamedQuery(name="LocationNote.findAll", query="SELECT l FROM LocationNote l"),
@NamedQuery(name="LocationNote.findByLocId", query="SELECT l FROM LocationNote l where l.location.locationNumber = :locationNumber"),
@NamedQuery(name="LocationNote.findByByUserDate", query="SELECT l FROM LocationNote l where l.user = :userId and l.time_Stamp >= :fromDate and  l.time_Stamp < :toDate"),
@NamedQuery(name="LocationNote.findByUserId", query="SELECT l FROM LocationNote l where l.user = :userId")

})
public class LocationNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int locNoteId;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="locationNumber")
	private Location location;	

	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;

	private String user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date followUp;
		
	private String projectId;	
	
	private String status;
	
	private String user_Created;

	public LocationNote() {
	}

	public int getNoteId() {
		return this.locNoteId;
	}

	public void setNoteId(int noteId) {
		this.locNoteId = noteId;
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
	public Date getFollowUp() {
		return followUp;
	}

	public void setFollowUp(Date followUp) {
		this.followUp = followUp;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser_Created() {
		return user_Created;
	}

	public void setUser_Created(String user_Created) {
		this.user_Created = user_Created;
	}	
}