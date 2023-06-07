package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.User;

public interface CartService {

	//save cart
	Cart saveCart(Cart cart);

	//get user details by book id
	List<Cart> getCartByBookId(Integer bookId);
	
}
