package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Repository.CartRepo;
import com.project.bookstore.Service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;
	
	
	@Override
	public Cart saveCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartRepo.save(cart);
		
	}


	@Override
	public List<Cart> getCartByBookId(Integer bookId) {
		// TODO Auto-generated method stub
		List<Cart> findAllByBookId = cartRepo.findAllByBookId(bookId);
		
		return findAllByBookId;
	}
	
	
	
}
