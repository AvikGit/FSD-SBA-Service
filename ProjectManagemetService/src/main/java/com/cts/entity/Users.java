package com.cts.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQuery(name="findUsers", query="from Users u")
@NamedQuery(name="findUserById", query="select u from Users u where u.userId=:userId")
public class Users {
	
	public Users(){}

	public Users(int employeeId, String firstName, String lastName) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public Users(int userId, int employeeId, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	int userId;
	@Column(name="employee_id")
	int employeeId;
	String firstName;
	String lastName;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="users")
	Set<Task> tasks;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="users")
	Set<Project> project;
	
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", employeeId=" + employeeId + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}
	
	
	
}
