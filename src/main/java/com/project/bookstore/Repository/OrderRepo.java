package com.project.bookstore.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
	
	List<Order> findByOrderDateBetween(Date fromDate, Date toDate);

}
