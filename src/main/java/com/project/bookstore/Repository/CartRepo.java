package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer>{

	List<Cart> findAllByUserId(Integer userId);

}
