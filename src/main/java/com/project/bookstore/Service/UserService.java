package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.User;

public interface UserService {
     //add user
	
	User addUser(User user);
	
	//get user by id
	
	User getUserById(Integer userId);
	
	//get all user
	
	List<User> getAllUser();
	
	
}
