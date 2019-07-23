


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.controller.UserRestController;
import com.cts.entity.UserList;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class TestUserManager {
	
	@Autowired
	UserRestController controller;

	
	@Test
	public void testCreateUser() {
		
		UserList userList = new UserList();		
		userList.setEmployeeId(101010);
		userList.setFirstName("Avik");
		userList.setLastName("UT");
		controller.createUser(userList);
		
	}
	
	@Test
	public void testFetchUsers(){
		
		controller.fetchUsers();
	}


	@Test
	public void testFetchUser(){
		controller.fetchUser(165);
	}
	
	@Test
	public void testDeleteUser(){
		controller.deleteUser(183);
	}

	
	@Test
	public void testUpdateUser() {
		UserList userList = new UserList();		
		userList.setUserId(183);
		userList.setEmployeeId(101010);
		userList.setFirstName("Avik");
		userList.setLastName("UT");
		controller.updateUser(userList);

	}
	
}
