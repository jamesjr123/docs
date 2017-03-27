package org.atp25d.model;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the user_access database table.
 * 
 */
@Entity
@Table(name="user_access")
@NamedQueries({
@NamedQuery(name="UserAccess.findAll", query="SELECT u FROM UserAccess u"),
@NamedQuery(name="UserAccess.findUserAccess", query="SELECT u FROM UserAccess u where  u.userId = :userId and u.userTask = :task")
})

public class UserAccess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iduser_access")
	private int iduserAccess;

	private String userAccess;

	private String userId;

	private String userTask;

	public UserAccess() {
	}

	public int getIduserAccess() {
		return this.iduserAccess;
	}

	public void setIduserAccess(int iduserAccess) {
		this.iduserAccess = iduserAccess;
	}

	public String getUserAccess() {
		return this.userAccess;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserTask() {
		return this.userTask;
	}

	public void setUserTask(String userTask) {
		this.userTask = userTask;
	}

}