package com.cts.entity;

public class ParentTaskList {
	
	public ParentTaskList(){
		
	}

	int parentId;	
	String parentTaskDesc;	

	

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

	@Override
	public String toString() {
		return "Parent Task [parentId=" + parentId + " parentTask=" + parentTaskDesc + "]";
	}

	public ParentTaskList(int parentId, String parentTaskDesc) {
		super();
		this.parentId = parentId;
		this.parentTaskDesc = parentTaskDesc;
	}

	
}
