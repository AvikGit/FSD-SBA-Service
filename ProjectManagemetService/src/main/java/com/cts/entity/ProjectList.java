package com.cts.entity;

public class ProjectList {
	
	public ProjectList(){
		
	}

	int projectId;
	String project;
	int taskCount;
	int completedTaskCount;
	String stStartDate;	
	String stEndDate;	
	int priority;
	int userId;

	

	public int getUserId() {
		return userId;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}



	public int getProjectId() {
		return projectId;
	}



	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}



	public String getProject() {
		return project;
	}



	public void setProject(String project) {
		this.project = project;
	}



	public int getTaskCount() {
		return taskCount;
	}



	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}



	public int getCompletedTaskCount() {
		return completedTaskCount;
	}



	public void setCompletedTaskCount(int completedTaskCount) {
		this.completedTaskCount = completedTaskCount;
	}



	public String getStStartDate() {
		return stStartDate;
	}



	public void setStStartDate(String stStartDate) {
		this.stStartDate = stStartDate;
	}



	public String getStEndDate() {
		return stEndDate;
	}



	public void setStEndDate(String stEndDate) {
		this.stEndDate = stEndDate;
	}



	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}



	public ProjectList(String project, String stStartDate, String stEndDate, int priority) {
		super();
		this.project = project;
		this.stStartDate = stStartDate;
		this.stEndDate = stEndDate;
		this.priority = priority;
	}
}
