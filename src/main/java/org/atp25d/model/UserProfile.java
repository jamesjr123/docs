package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the user_profile database table.
 * 
 */
@Entity
@Table(name="user_profile")
@NamedQueries({
@NamedQuery(name="UserProfile.findAll", query="SELECT u FROM UserProfile u"),
@NamedQuery(name="UserProfile.findById", query="SELECT u FROM UserProfile u where u.userId = :userId")
})
public class UserProfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="iduser_profile")
	private int iduserProfile;

	private byte adminUser;

	@Lob
	private String companyId;

	@Lob
	private String todoistToken;

	@Lob
	private String userId;

	public UserProfile() {
	}

	public int getIduserProfile() {
		return this.iduserProfile;
	}

	public void setIduserProfile(int iduserProfile) {
		this.iduserProfile = iduserProfile;
	}

	public byte getAdminUser() {
		return this.adminUser;
	}

	public void setAdminUser(byte adminUser) {
		this.adminUser = adminUser;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getTodoistToken() {
		return this.todoistToken;
	}

	public void setTodoistToken(String todoistToken) {
		this.todoistToken = todoistToken;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}