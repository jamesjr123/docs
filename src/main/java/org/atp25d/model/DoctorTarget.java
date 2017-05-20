package org.atp25d.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the doctor_targets database table.
 * 
 */
@Entity
@Table(name="doctor_targets")
@NamedQueries({
@NamedQuery(name="DoctorTarget.findAll", query="SELECT d FROM DoctorTarget d"),
@NamedQuery(name="DoctorTarget.findByTargets", query="SELECT d FROM DoctorTarget d where d.targetType=:type and d.target in :targets")
})
public class DoctorTarget implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int targetId;

	private int doctorId;

	private String target;

	private String targetType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time_Stamp;

	private String user;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name="doctorNumber")
	private Doctor doctor;		
	
	public DoctorTarget() {
	}

	public int getTargetId() {
		return this.targetId;
	}

	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}

	public int getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTargetType() {
		return this.targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
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