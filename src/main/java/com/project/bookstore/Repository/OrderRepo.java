package com.project.bookstore.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

	@Query("SELECT o FROM Order o where DATE(o.orderDate) >= :fromDate AND DATE(o.orderDate) <= :toDate")
	List<Order> findByOrderDateBetween(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
