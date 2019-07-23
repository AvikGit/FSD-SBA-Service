package com.cts.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.cts.entity.UserList;
import com.cts.entity.Users;
import com.cts.repository.UsersRepository;

@RestController
@RequestMapping("/projectmanagementuserapi")
@CrossOrigin("*")
public class UserRestController {
	
	
	@Autowired
	UsersRepository userRepository;
	
	/**
	 * This method is used to get all the user list from database
	 * @return
	 */
	@RequestMapping(path="/users", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<UserList>>fetchUsers(){
		
		List<Users> users = userRepository.findUsers();
		UserList usersList = new UserList();
		List<UserList> allUsers = new ArrayList<UserList>();
		for (Users user : users) {
			usersList = new UserList();
			usersList.setUserId(user.getUserId());
			usersList.setFirstName(user.getFirstName());
			usersList.setLastName(user.getLastName());
			usersList.setEmployeeId(user.getEmployeeId());
			allUsers.add(usersList);
		}
		
		return new ResponseEntity<List<UserList>>(allUsers, HttpStatus.OK);
	}
	

	/**
	 * This method id used to fetch a particular user from database 
	 * @param id
	 * @return
	 */
	@RequestMapping(path="/user/{id}", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<UserList>fetchUser(@PathVariable("id") int id){
		List<Users> users = userRepository.findUser(id);
		Users user = null;
		if(users.size() > 0) {
			user = users.get(0);
		}

		UserList userList = new UserList();
		userList.setUserId(user.getUserId());
		userList.setFirstName(user.getFirstName());		
		userList.setLastName(user.getLastName());
		userList.setEmployeeId(user.getEmployeeId());
		return new ResponseEntity<UserList>(userList, HttpStatus.OK);
	}
	

	/**
	 * This method is used to delete user from database
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id){
		System.out.println("ID recieved : " + id);
		userRepository.removeUser(id);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
	

	/**
	 * This method is used to create new User
	 * @param projectList
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody UserList userList) {
		System.out.println("Creating User " + userList);
		
		try{
			List<Users> oldUsers = userRepository.findById(userList.getUserId());
			if(oldUsers.size() > 0){
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
			else{
				
				Users user = new Users(userList.getEmployeeId(), userList.getFirstName(), userList.getLastName());
				
				userRepository.addUser(user);
				return new ResponseEntity<Void>(HttpStatus.CREATED);

			}
		}
		catch(Exception nre){
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}
	

	/**
	 * This method is used to update a user on the basis of user id
	 * @param userList
	 * @return
	 */
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@RequestBody UserList userList) {
		
		try{
			Users user = new Users(userList.getUserId(), userList.getEmployeeId(), userList.getFirstName(), userList.getLastName());
			
			userRepository.updateUser(user);

			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		}
		catch(Exception nre){
						
			return new ResponseEntity<Void>(HttpStatus.EXPECTATION_FAILED);
		}	
	}
		
}
