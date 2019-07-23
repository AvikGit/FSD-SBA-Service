


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.controller.ProjectRestController;
import com.cts.entity.ProjectList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestProjectManager {
	
	
	@Autowired
	ProjectRestController controller;

	
	@Test
	public void testCreateProject() {
		
		ProjectList projectList = new ProjectList();
		//projectList.setProjectId(301);
		projectList.setPriority(3);
		projectList.setUserId(165);
		projectList.setProject("UT Project Created");
		projectList.setStStartDate("2019-07-11");
		projectList.setStEndDate("2019-07-18");
		controller.createProject(projectList);
		
	}
	
	@Test
	public void testFetchProjects(){
		
		controller.fetchProjects();
	}


	@Test
	public void testFetchProject(){
		controller.fetchProject(181);
	}
	
	@Test
	public void testDeleteProject(){
		controller.deleteProject(229);
	}

	@Test
	public void testUpdateProject() {
		ProjectList projectList = new ProjectList();
		projectList.setProjectId(182);
		projectList.setPriority(3);
		projectList.setUserId(165);
		projectList.setProject("Updated UT Project");
		projectList.setStStartDate("2019-07-11");
		projectList.setStEndDate("2019-07-21");
		controller.updateProject(projectList);

	}
	
	
}
