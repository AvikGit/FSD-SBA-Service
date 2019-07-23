package com.cts.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cts.entity.Project;
import com.cts.entity.ProjectList;
import com.cts.entity.Users;
import com.cts.repository.ProjectRepository;

@RestController
@RequestMapping("/projectmanagementprojectapi")
@CrossOrigin("*")
public class ProjectRestController {
	
	
	@Autowired
	ProjectRepository projectRepository;
	
	/**
	 * This method is used to get all the project list from database
	 * @return
	 */
	@RequestMapping(path="/projects", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ProjectList>>fetchProjects(){
		
		List<Project> projects = projectRepository.findProjects();
		ProjectList projectList = new ProjectList();
		List<ProjectList> allProjects = new ArrayList<ProjectList>();
		for (Project project : projects) {
			projectList = new ProjectList();
			projectList.setProjectId(project.getProjectId());
			projectList.setProject(project.getProject());
			projectList.setStStartDate(getStringdate(project.getStartDate()));
			projectList.setStEndDate(getStringdate(project.getEndDate()));
			projectList.setPriority(project.getPriority());
			projectList.setUserId(project.getUsers().getUserId());
			if(project.getTasks() != null && project.getTasks().size() > 0)
				projectList.setTaskCount(project.getTasks().size());
			allProjects.add(projectList);
		}
		
		return new ResponseEntity<List<ProjectList>>(allProjects, HttpStatus.OK);
	}
	

	/**
	 * This method id used to fetch a particular project from database 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/project/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ProjectList>fetchProject(@PathVariable("id") int id){
		List<Project> projects = projectRepository.findProject(id);
		Project project = null;
		if(projects.size() > 0) {
			project = projects.get(0);
		}

		ProjectList projectList = new ProjectList();
		projectList.setProjectId(project.getProjectId());
		projectList.setProject(project.getProject());
		
		projectList.setStStartDate(getStringdate(project.getStartDate()));
		projectList.setStEndDate(getStringdate(project.getEndDate()));
		projectList.setPriority(project.getPriority());
		projectList.setUserId(project.getUsers().getUserId());
		return new ResponseEntity<ProjectList>(projectList, HttpStatus.OK);
	}
	

	/**
	 * This method is used to delete project from database
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProject(@PathVariable("id") int id){
		System.out.println("ID recieved : " + id);
		projectRepository.removeProject(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	

	/**
	 * This method is used to create new project
	 * @param projectList
	 * @return
	 */
	@RequestMapping(value = "/project", method = RequestMethod.POST)
	public ResponseEntity<Void> createProject(@RequestBody ProjectList projectList) {
		
		try{
			List<Project> oldProjects = projectRepository.findById(projectList.getProjectId());
			if(oldProjects.size() > 0){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else{
				Users user = new Users();
				user.setUserId(projectList.getUserId());
				Project project = new Project(projectList.getProject(), getSqlDate(projectList.getStStartDate()), getSqlDate(projectList.getStEndDate()),
						projectList.getPriority());
				project.setUsers(user);
				projectRepository.addProject(project);
				return new ResponseEntity<Void>(HttpStatus.CREATED);

			}
		}
		catch(Exception nre){
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}



	/**
	 * This method is used to update a project on the basis of project id
	 * @param projectList
	 * @return
	 */
	@RequestMapping(value = "/project", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateProject(@RequestBody ProjectList projectList) {
		System.out.println("Update Project " + projectList);
		
		try{
			Users user = new Users();
			user.setUserId(projectList.getUserId());
			Project project = new Project(projectList.getProjectId(),projectList.getProject(), getSqlDate(projectList.getStStartDate()), getSqlDate(projectList.getStEndDate()),
					projectList.getPriority());
			project.setUsers(user);
			projectRepository.updateProject(project);

			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		}
		catch(Exception nre){
						
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
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
