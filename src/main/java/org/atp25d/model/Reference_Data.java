package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the reference_data database table.
 * 
 */
@Entity
@Table(name="reference_data")
@NamedQueries({
@NamedQuery(name="Reference_Data.findAll", query="SELECT r FROM Reference_Data r"),
@NamedQuery(name="Reference_Data.findByListByRefType", query="SELECT r FROM Reference_Data r where r.refType = :refType")
})

public class Reference_Data implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codeNumber;

	private String code;

	private String compId;

	private String refType;

	private String userId;

	private String value;

	public Reference_Data() {
	}

	public int getCodeNumber() {
		return this.codeNumber;
	}

	public void setCodeNumber(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCompId() {
		return this.compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getRefType() {
		return this.refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}