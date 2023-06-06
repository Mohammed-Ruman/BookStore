package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.User;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.UserRepo;
import com.project.bookstore.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public User getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not"
				+ "found with given id: " + userId));
		
		
		return user;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

}
