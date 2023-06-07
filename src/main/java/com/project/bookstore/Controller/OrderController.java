package com.project.bookstore.Controller;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.Order;
import com.project.bookstore.Payload.LoggerOutputStream;
import com.project.bookstore.Payload.OrderInfo;
import com.project.bookstore.Service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	// save order
	@PostMapping("/save")
	public ResponseEntity<Object> saveOrder(@RequestParam Integer userId) {

		Order saveOrder = orderService.saveOrder(userId);

		return new ResponseEntity<Object>(saveOrder, HttpStatus.OK);

	}

	// to get order info within given date
	@GetMapping("/find")
	public ResponseEntity<List<OrderInfo>> getOrdersByDateRange(
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate) {
		List<OrderInfo> ordersByDateRange = orderService.getOrdersByDateRange(fromDate, toDate);
		return new ResponseEntity<List<OrderInfo>>(ordersByDateRange, HttpStatus.OK);
	}

}
