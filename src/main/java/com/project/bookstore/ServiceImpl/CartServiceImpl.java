package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Repository.CartRepo;
import com.project.bookstore.Repository.UserRepo;
import com.project.bookstore.Service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private BookRepo bookRepo;

	@Override
	public Cart saveCart(Cart cart) {
		// TODO Auto-generated method stub
		if(userRepo.findById(cart.getUserId()).isEmpty()) {
			throw new ResourceNotFoundException("User not found with id: " + cart.getUserId());
		}
		
		if(bookRepo.findById(cart.getBookId()).isEmpty()) {
			throw new ResourceNotFoundException("Book not found with id: " + cart.getBookId());
		}
		
		Cart cartByBookId = cartRepo.findByUserIdAndBookIdAndIsPurchasedFalse(cart.getUserId(), cart.getBookId());
		if (cartByBookId == null) {
			return cartRepo.save(cart);
		} else {
			cartByBookId.setQuantity(cart.getQuantity() + cartByBookId.getQuantity());
			return cartRepo.save(cartByBookId);
		}
	}

	@Override
	public List<Cart> getCartByBookId(Integer bookId) {
		// TODO Auto-generated method stub
		return cartRepo.findAllByBookId(bookId);
	}

	@Override
	public boolean deleteCartById(Integer cartId) {
		// TODO Auto-generated method stub
		return cartRepo.findById(cartId).map((cart) -> {
			cartRepo.delete(cart);
			return true;
		}).orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));
	}

	@Override
	public List<Cart> getAllByUserId(Integer userId) {
		// TODO Auto-generated method stub
		
		return cartRepo.findAllByUserIdAndIsPurchasedFalse(userId);
	}

}
