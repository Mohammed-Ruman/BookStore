package com.project.bookstore.Payload;

import java.util.Date;

import com.project.bookstore.Entity.Order;
import com.project.bookstore.Entity.User;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class OrderInfo {

	private User user;
	private Order order;
	private Date orderDate;

	public OrderInfo(User user, Order order, Date orderDate) {
		this.order = order;
		this.user = user;
		this.orderDate = orderDate;
	}

}
