package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
		return userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not" + "found with given id: " + userId));
	}

	@Override
	public List<User> getAllUser(Integer pageNumber, Integer pageSize, String keywords) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		if (keywords.isBlank()) {
			return userRepo.findAll(pageable).getContent();
		} else {
			return userRepo.searchUserByUserName(keywords, pageable).getContent();
		}
	}

	// delete user
	public boolean deleteUserById(Integer userId) {
		userRepo.delete(getUserById(userId));
		return true;
	}

	@Override
	public User updateUserById(User user, Integer userId) {
		// TODO Auto-generated method stub
		return userRepo.findById(userId).map((existingUser) -> {
			existingUser.setUserName(user.getUserName());
			existingUser.setEmail(user.getEmail());
			existingUser.setAddress(user.getAddress());
			return userRepo.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found "));
	}

}
