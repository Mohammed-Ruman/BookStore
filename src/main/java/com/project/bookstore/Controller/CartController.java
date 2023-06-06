package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@PostMapping("/save")
	public ResponseEntity<Object> saveCart(@RequestBody Cart cart){		
		return new ResponseEntity<Object>(cartService.saveCart(cart),HttpStatus.CREATED);
	}
}
