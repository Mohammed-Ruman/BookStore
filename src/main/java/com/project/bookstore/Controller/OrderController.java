package com.project.bookstore.Controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// save order
	@PostMapping
	public ResponseEntity<Object> saveOrder(@RequestParam Integer userId) {

		return new ResponseEntity<Object>(orderService.saveOrder(userId), HttpStatus.OK);

	}

	// to get order info within given date
	@GetMapping("/find")
	public ResponseEntity<Object> getOrdersByDateRange(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {
		return new ResponseEntity<Object>(orderService.getOrdersByDateRange(fromDate, toDate), HttpStatus.OK);
	}

	// to delete an order
	@DeleteMapping
	public ResponseEntity<Object> deleteOrder(@RequestParam Integer orderId) {
		orderService.deleteOrder(orderId);
		return ResponseEntity.ok("success : order deleted");
	}

}
