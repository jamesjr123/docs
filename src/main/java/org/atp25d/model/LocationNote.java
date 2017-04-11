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
@NamedQuery(name="LocationNote.findByLocId", query="SELECT l FROM LocationNote l where l.locationNumber = :locationNumber")
})
public class LocationNote implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int locNoteId;

	private int locationNumber;

	private String notes;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;

	private String user;

	public LocationNote() {
	}

	public int getNoteId() {
		return this.locNoteId;
	}

	public void setNoteId(int noteId) {
		this.locNoteId = noteId;
	}

	public int getLocationNumber() {
		return this.locationNumber;
	}

	public void setLocationNumber(int locationNumber) {
		this.locationNumber = locationNumber;
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

}