package com.project.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.OrderItems;

public interface OrderItemRepo extends JpaRepository<OrderItems, Integer>{

}
