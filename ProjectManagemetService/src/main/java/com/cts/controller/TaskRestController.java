package com.cts.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.TaskList;
import com.cts.entity.Users;
import com.cts.entity.ParentTask;
import com.cts.entity.ParentTaskList;
import com.cts.entity.Project;
import com.cts.entity.Task;
import com.cts.repository.TaskRepository;

@RestController
@RequestMapping("/projectmanagementtaskapi")
@CrossOrigin("*")
public class TaskRestController {
	
	
	@Autowired
	TaskRepository taskRepository;
	
	/**
	 * This method is used to get all the task list from database
	 * @return
	 */
	@RequestMapping(path="/tasks", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<TaskList>>fetchTasks(){
		
		List<Task> tasks = taskRepository.findTasks();
		TaskList allTask = new TaskList();
		List<TaskList> allTasks = new ArrayList<TaskList>();
		for (Task task : tasks) {
			allTask = new TaskList();
			allTask.setTaskId(task.getTaskId());
			allTask.setTask(task.getTask());
			allTask.setStStartDate(getStringdate(task.getStartDate()));
			allTask.setStEndDate(getStringdate(task.getEndDate()));
			allTask.setPriority(task.getPriority());
			allTask.setParentId(task.getParentTask().getParentId());
			allTask.setParentTask(task.getParentTask().getParentTaskDesc());
			allTask.setProjectId(task.getProject().getProjectId());
			allTask.setUserId(task.getUsers().getUserId());
			allTask.setTaskStatus(task.getStatus());
			allTasks.add(allTask);
		}
		
		return new ResponseEntity<List<TaskList>>(allTasks, HttpStatus.OK);
	}
	
	/**
	 * This method is used to get all the parent task list from database
	 * @return
	 */
	@RequestMapping(path="/parentTasks", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ParentTaskList>>fetchParentTasks(){
		
		List<ParentTask> parentTasks = taskRepository.findParentTaskList();
		ParentTaskList allParentTask = new ParentTaskList();
		List<ParentTaskList> allParentTasks = new ArrayList<ParentTaskList>();
		for (ParentTask parentTask : parentTasks) {
			allParentTask = new ParentTaskList();
			allParentTask.setParentId(parentTask.getParentId());
			allParentTask.setParentTaskDesc(parentTask.getParentTaskDesc());
			allParentTasks.add(allParentTask);
		}
		
		return new ResponseEntity<List<ParentTaskList>>(allParentTasks, HttpStatus.OK);
	}
	

	/**
	 * This method id used to fetch a particular task from database 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/task/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<TaskList>fetchTask(@PathVariable("id") int id){
		List<Task> tasks = taskRepository.findTask(id);
		Task task = null;
		if(tasks.size() > 0) {
			task = tasks.get(0);
		}

		TaskList allTask = new TaskList();
		allTask.setTaskId(task.getTaskId());
		allTask.setTask(task.getTask());
		
		allTask.setStStartDate(getStringdate(task.getStartDate()));
		allTask.setStEndDate(getStringdate(task.getEndDate()));
		allTask.setPriority(task.getPriority());
		allTask.setParentId(task.getParentTask().getParentId());
		allTask.setParentTask(task.getParentTask().getParentTaskDesc());
		allTask.setUserId(task.getUsers().getUserId());
		allTask.setProjectId(task.getProject().getProjectId());
		allTask.setTaskStatus(task.getStatus());
		return new ResponseEntity<TaskList>(allTask, HttpStatus.OK);
	}
	

	/**
	 * This method is used to delete task from database
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/task/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteTask(@PathVariable("id") int id){
		System.out.println("ID recieved : " + id);
		try {
			taskRepository.removeTask(id);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	

	/**
	 * This method is used to create new task
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/task", method = RequestMethod.POST)
	public ResponseEntity<Void> createTask(@RequestBody TaskList allTask) {
		System.out.println("Creating Task " + allTask);
		
		try{
			List<Task> oldTasks = taskRepository.findById(allTask.getTaskId());
			if(oldTasks.size() > 0){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else{
				ParentTask pt = new ParentTask(allTask.getParentTask());
				pt.setParentId(allTask.getParentId());
				Project project = new Project();
				project.setProjectId(allTask.getProjectId());
				Users user = new Users();
				user.setUserId(allTask.getUserId());
				Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
						allTask.getPriority());

				task.setStatus(1);
				task.setParentTask(pt);
				task.setProject(project);
				task.setUsers(user);				
				
				taskRepository.addTask(task);
				return new ResponseEntity<Void>(HttpStatus.CREATED);

			}
		}
		catch(Exception nre){
			
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}
	
	/**
	 * This method is used to create new parent task
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/parentTask", method = RequestMethod.POST)
	public ResponseEntity<Void> createParentTask(@RequestBody TaskList allTask) {
		System.out.println("Creating Parent Task " + allTask);
		
		try{
			List<ParentTask> oldParentTasks = taskRepository.findPTById(allTask.getTaskId());
			if(oldParentTasks.size() > 0){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else{
				ParentTask pt = new ParentTask(allTask.getTask());
								
				taskRepository.addParentTask(pt);
				return new ResponseEntity<Void>(HttpStatus.CREATED);

			}
		}
		catch(Exception nre){
			
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}
	
	/**
	 * This method is used to update a task on the basis of task id
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/task", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateTask(@RequestBody TaskList allTask) {
		System.out.println("Update Task " + allTask);
		
		try{
			Users user = new Users();
			user.setUserId(allTask.getUserId());
			Project project = new Project();
			project.setProjectId(allTask.getProjectId());
			ParentTask pt = new ParentTask();
			pt.setParentId(allTask.getParentId());
			Task task = new Task(allTask.getTask(), getSqlDate(allTask.getStStartDate()), getSqlDate(allTask.getStEndDate()),
					allTask.getPriority());
			task.setTaskId(allTask.getTaskId());
			task.setParentTask(pt);
			task.setUsers(user);
			task.setProject(project);
			
			taskRepository.updateTask(task);

			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		}
		catch(Exception nre){
			
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}
	

	/**
	 * This method is used to update task status to inactive
	 * @param allTask
	 * @return
	 */
	@RequestMapping(value = "/taskStatus/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> updateTaskStatus(@PathVariable("id") int id) {
		System.out.println("Update Task Status to Inactive, Id " + id);
		
		try{
			taskRepository.updateTaskStatus(id);
		}
		catch(Exception nre){
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	

	
	/**
	 * This method is used to convert String date to SQL date
	 * @param stDate
	 * @return
	 */
	public Date getSqlDate(String stDate) {
		java.sql.Date sqlStartDate = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date;
		boolean isNotParse = false;
		try {
			if(null != stDate) {
				date = sdf1.parse(stDate);
				sqlStartDate = new java.sql.Date(date.getTime());
			}
		} catch (ParseException e) {
			isNotParse = true;
		}

		if(isNotParse){
			try{
				SimpleDateFormat sdf11 = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date date11 = sdf11.parse(stDate);
	
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
				String date1 = sdf2.format(date11);
				
	
				java.util.Date date2 = sdf2.parse(date1);
				
				sqlStartDate = new java.sql.Date(date2.getTime());
				}
			catch(ParseException e) {
				isNotParse = true;
				e.printStackTrace();
			}
		}
		return sqlStartDate;
	}

	
	/**
	 * This method is used to get String date from SQL date
	 * @param sqlDate
	 * @return
	 */
	public String getStringdate(Date sqlDate) {
		String stDate = "";
		if(null != sqlDate) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
			stDate = df.format(sqlDate); 
		}
		return stDate;
	}
	
}
