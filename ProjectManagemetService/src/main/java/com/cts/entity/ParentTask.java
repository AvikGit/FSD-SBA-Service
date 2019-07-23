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
@NamedQuery(name="findPTById", query="select pt from ParentTask pt where pt.parentId=:parentId")
@NamedQuery(name="findParentTasks", query="from ParentTask pt")
public class ParentTask {
	
	public ParentTask(){}

	public ParentTask(String parentTaskDesc) {
		super();
		this.parentTaskDesc = parentTaskDesc;
	}

	@Override
	public String toString() {
		return "ParentTask [parentId=" + parentId + ", parentTask=" + parentTaskDesc + "]";
	}



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="parent_id")
	int parentId;
	@Column(name="parent_task")
	String parentTaskDesc;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="parentTask")
    Set<Task> tasks;

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getParentTaskDesc() {
		return parentTaskDesc;
	}

	public void setParentTaskDesc(String parentTaskDesc) {
		this.parentTaskDesc = parentTaskDesc;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}
	
	
}
