package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bookstore.Entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	List<Cart> findAllByUserId(Integer userId);

	@Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.isPurchased = false")
	List<Cart> findAllByUserIdAndIsPublishedFalse(Integer userId);

	List<Cart> findAllByBookId(Integer bookId);

}
