package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.User;
import com.project.bookstore.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	// add user

	@PostMapping
	public ResponseEntity<Object> addUser(@RequestBody User user) {
		return new ResponseEntity<Object>(userService.addUser(user), HttpStatus.CREATED);
	}

	// get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getUserById(@PathVariable Integer userId) {
		return new ResponseEntity<Object>(userService.getUserById(userId), HttpStatus.OK);
	}

	// get all user
	@GetMapping
	public ResponseEntity<Object> getAllUser(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "1",required = false) Integer pageSize,
			@RequestParam(value = "keywords",defaultValue = "",required = false) String keyword
			) {
		return new ResponseEntity<Object>(userService.getAllUser(pageNumber, pageSize, keyword), HttpStatus.OK);
	}
	
	//delete user
	@DeleteMapping
	public ResponseEntity<Object> deleteUser(@RequestParam(value = "userId") Integer userId) {
		userService.deleteUserById(userId);
		return ResponseEntity.ok("Success : User deleted");
	}
	
	//update user
	@PutMapping
	public ResponseEntity<Object> updateUser(@RequestBody User user ,@RequestParam(value = "userId") Integer userId) {
		return new ResponseEntity<Object>(userService.updateUserById(user, userId), HttpStatus.OK);
	}
	
}
