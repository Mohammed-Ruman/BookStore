package com.project.bookstore.Service;

import java.util.Date;
import java.util.List;

import com.project.bookstore.Entity.Order;
import com.project.bookstore.Payload.OrderInfo;

public interface OrderService {

	// save order
	Order saveOrder(Integer userId);

	// to find order details between provided date
	List<OrderInfo> getOrdersByDateRange(Date fromDate, Date toDate);
	
	//to delete order
	void deleteOrder(Integer orderId);
	
	//to find order details by orderId
	Order getOrderById(Integer orderId);

}
