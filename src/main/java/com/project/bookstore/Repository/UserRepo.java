package com.project.bookstore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.userName like %:key%")
	Page<User> searchUserByUserName(@Param("key") String keywords, Pageable pageable);
}
