package com.project.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
