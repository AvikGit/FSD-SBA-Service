


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.entity.TaskList;
import com.cts.controller.TaskRestController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestTaskManager {
	
	@Autowired
	TaskRestController controller;
	
	@Test
	public void testCreateParentTask() {
		
		TaskList allTask = new TaskList();
		allTask.setParentTask("Parent Task UT");
		controller.createParentTask(allTask);
				
	}
	
	@Test
	public void testCreateTask() {
		
		TaskList allTask = new TaskList();
		
		allTask.setParentId(167);
		allTask.setProjectId(166);
		allTask.setUserId(165);
		allTask.setTask("Task UT Create");
		allTask.setStStartDate("2019-07-22");
		allTask.setStEndDate("2019-07-29");
		allTask.setPriority(3);
		
		controller.createTask(allTask);
		
	}
	
	@Test
	public void testFetchTasks(){
		
		controller.fetchTasks();
	}
	
	@Test
	public void testFetchParentTasks(){
		
		controller.fetchParentTasks();
	}

	@Test
	public void testFetchTask(){
		controller.fetchTask(196);
	}
	
	@Test
	public void testDeleteTask(){
		controller.deleteTask(216);
	}
	
	@Test
	public void testUpdateTaskStatus(){
		controller.updateTaskStatus(224);
	}

	@Test
	public void testUpdateTask() {
		TaskList allTask = new TaskList();
		allTask.setTaskId(193);
		allTask.setParentId(167);
		allTask.setProjectId(166);
		allTask.setUserId(165);
		allTask.setTask("Task UT Create");
		allTask.setStStartDate("2019-07-22");
		allTask.setStEndDate("2019-07-29");
		allTask.setPriority(3);
		
		controller.updateTask(allTask);

	}
	
}
