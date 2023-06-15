package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bookstore.Entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	List<Cart> findAllByUserId(Integer userId);

	@Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.isPurchased = false")
	List<Cart> findAllByUserIdAndIsPurchasedFalse(Integer userId);

	List<Cart> findAllByBookId(Integer bookId);
	
	@Query("SELECT c FROM Cart c WHERE c.order.orderId = :orderId")
	List<Cart>  findAllByOrderId(Integer orderId);
	
	@Query("SELECT c FROM Cart c where c.userId = :userId AND c.isPurchased = false AND c.bookId = :bookId")
	Cart findByUserIdAndBookIdAndIsPurchasedFalse(Integer userId,Integer bookId);

}
