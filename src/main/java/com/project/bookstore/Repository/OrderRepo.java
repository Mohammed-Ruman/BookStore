package com.project.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {

}
