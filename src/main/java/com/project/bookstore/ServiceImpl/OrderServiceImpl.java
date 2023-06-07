package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.Order;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Payload.OrderInfo;
import com.project.bookstore.Repository.CartRepo;
import com.project.bookstore.Repository.OrderRepo;
import com.project.bookstore.Service.BookService;
import com.project.bookstore.Service.OrderService;
import com.project.bookstore.Service.UserService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService UserService;
	
	@Override
	public Order saveOrder(Integer userId) {
		// TODO Auto-generated method stub
		List<Cart> cartList = cartRepo.findAllByUserId(userId);
		double total=0.0;
		
		for (Cart cart : cartList) {
	        Book book = bookService.getBookById(cart.getBookId());
	        total += book.getPrice() * cart.getQuantity();
	    }
		
		Order newOrder=new Order();
		
		newOrder.setCartItems(cartList);
		newOrder.setTotalPrice(total);
		newOrder.setOrderDate(new Date());
		newOrder.setUser(UserService.getUserById(userId));
		try {
		Order savedOrder = orderRepo.save(newOrder);
		for (Cart cart : cartList) {
			cart.setOrder(savedOrder);
			cartRepo.save(cart);
		}
		
		return savedOrder;
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println( e.getMessage());
		}
		return null;
	}

	@Override
	public List<OrderInfo> getOrdersByDateRange(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub
			List<Order> findByDateBetweenOrders = orderRepo.findByOrderDateBetween(fromDate, toDate);
			
			List<OrderInfo> orderInfoList = new ArrayList<>();

	        for (Order order : findByDateBetweenOrders) {
	            
	        	User user = order.getUser();
	            OrderInfo orderInfo = new OrderInfo(user,order, order.getOrderDate());
	            orderInfoList.add(orderInfo);
	        }
		
		return orderInfoList;
	}

}
