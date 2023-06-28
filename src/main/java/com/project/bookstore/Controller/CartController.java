package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	
	//save cart
	@PostMapping
	public ResponseEntity<Object> saveCart(@RequestBody Cart cart){		
		return new ResponseEntity<Object>(cartService.saveCart(cart),HttpStatus.CREATED);
	}
	
	//delete cart
	@DeleteMapping("/{cartId}")
	public ResponseEntity<Object> deleteCart(@PathVariable Integer cartId){	
		if(cartService.deleteCartById(cartId)) {
			return new ResponseEntity<Object>("success : cart deleted successfullly",HttpStatus.CREATED);
		}
		return null;
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getCartByUserId(@PathVariable Integer userId){			
		return ResponseEntity.ok(cartService.getAllByUserId(userId));
	}
}
