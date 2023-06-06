package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.Order;
import com.project.bookstore.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	//save order
		@PostMapping("/save/{userId}")
		public ResponseEntity<Order> saveOrder(@PathVariable Integer userId){		
			return new ResponseEntity<Order>(orderService.saveOrder(userId),HttpStatus.CREATED);
		}
}
