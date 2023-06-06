package com.project.bookstore.Service;

import org.springframework.util.MultiValueMap;

import com.project.bookstore.Entity.Order;

public interface OrderService {

	Order saveOrder(Integer userId);

	
}
