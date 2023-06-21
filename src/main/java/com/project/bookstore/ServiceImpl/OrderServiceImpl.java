package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.Order;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
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
		List<Cart> cartList = cartRepo.findAllByUserIdAndIsPurchasedFalse(userId);

		if (cartList.isEmpty()) {
			throw new BadRequestException("error : empty cart");
		}

		double total = 0.0;

		for (Cart cart : cartList) {
			Book book = bookService.getBookById(cart.getBookId());
			total += book.getPrice() * cart.getQuantity();
		}

		Order newOrder = Order.builder().cartItems(cartList).totalPrice(total).orderDate(new Date())
				.user(UserService.getUserById(userId)).build();

		Order savedOrder = orderRepo.save(newOrder);
		cartList.forEach(cart -> {
			cart.setOrder(savedOrder);
			cart.setPurchased(true);
			cartRepo.save(cart);
		});

		return savedOrder;

	}

	@Override
	public List<OrderInfo> getOrdersByDateRange(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub

		List<OrderInfo> orderInfoList = new ArrayList<>();

		orderRepo.findByOrderDateBetween(fromDate, toDate).stream().forEach(order -> {
			orderInfoList.add(OrderInfo.builder().user(UserService.getUserById(order.getUser().getUserId()))
					.order(order).orderDate(order.getOrderDate()).build());
		});

		return orderInfoList;
	}

	@Override
	public void deleteOrder(Integer orderId) {
		// TODO Auto-generated method stub

		orderRepo.deleteById(orderId);

		// after deleting the order delete the cart associated with that order id
		cartRepo.findAllByOrderId(orderId).forEach(cart -> cartRepo.delete(cart));

	}

	@Override
	public Order getOrderById(Integer orderId) {
		// TODO Auto-generated method stub

		return orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with given orderId"));

	}

}
