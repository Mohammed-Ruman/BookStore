package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Cart;

public interface CartService {

	// save cart
	Cart saveCart(Cart cart);

	// get user details by book id
	List<Cart> getCartByBookId(Integer bookId);

	// delete cart
	boolean deleteCartById(Integer cartId);
	
	//get all cart by userid
	
	List<Cart> getAllByUserId(Integer userId);

}
