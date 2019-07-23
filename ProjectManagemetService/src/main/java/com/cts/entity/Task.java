package com.cts.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQuery(name="findTasks", query="from Task t")
@NamedQuery(name="findById", query="select t from Task t where t.taskId=:taskId")
public class Task {
	
	public Task(){}

	public Task(String task, Date startDate, Date endDate, int priority) {
		super();
		this.task = task;
		this.startDate = startDate;
		this.endDate = endDate;
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", task=" + task + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", priority=" + priority + ", Status=" + status +"]";
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_id")
	int taskId;
	String task;
	@Column(name="start_date")
	Date startDate;
	@Column(name="end_date")
	Date endDate;
	int priority;
	int status;

	@ManyToOne
	@JsonIgnore
	ParentTask parentTask;
	
	@ManyToOne
	@JsonIgnore
	Project project;
	
	@ManyToOne
	@JsonIgnore
	Users users;
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public ParentTask getParentTask() {
		return parentTask;
	}

	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param taskStatus the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	



	
	
	
}
