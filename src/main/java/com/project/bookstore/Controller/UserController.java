package com.project.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.User;
import com.project.bookstore.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	//add user
	
	@PostMapping("/save")
	public ResponseEntity<User> addUser(@RequestBody User user){
		User addUser = userService.addUser(user);
		return new ResponseEntity<User>(addUser,HttpStatus.CREATED);
	}
	
	
	//get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable Integer userId){
		User userById = userService.getUserById(userId);
		return new ResponseEntity<User>(userById, HttpStatus.OK);
	}
	
	//get all user
	@GetMapping("/")
	public ResponseEntity<List<User>> getAllUser(){
		return new ResponseEntity<List<User>>(userService.getAllUser(),HttpStatus.OK);
	}
}
