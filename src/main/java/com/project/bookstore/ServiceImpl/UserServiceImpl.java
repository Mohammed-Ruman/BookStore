package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.User;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.UserRepo;
import com.project.bookstore.Service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public User addUser(User user, Integer userId) {
		// TODO Auto-generated method stub
		if(userRepo.findByUserNameAndEmail(user.getUserName(), user.getEmail()).isPresent()) {
			throw new BadRequestException("Error : Duplicate user");
		}
		return userId==0? userRepo.save(user):updateUserById(user, userId);
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
		return keywords.isBlank()? userRepo.findAll(pageable).getContent():userRepo.searchUserByUserName(keywords, pageable).getContent();
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
			existingUser.setUserName(user.getUserName()==null? existingUser.getUserName():user.getUserName());
			existingUser.setEmail(user.getEmail()==null?existingUser.getEmail():user.getEmail());
			existingUser.setAddress(user.getAddress()==null?existingUser.getAddress():user.getAddress());
			return userRepo.save(existingUser);
		}).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found "));
	}

}
