package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		List<Cart> cartList = cartRepo.findAllByUserIdAndIsPublishedFalse(userId);
		double total = 0.0;

		for (Cart cart : cartList) {
			Book book = bookService.getBookById(cart.getBookId());
			total += book.getPrice() * cart.getQuantity();
		}

		Order newOrder = new Order();

		newOrder.setCartItems(cartList);
		newOrder.setTotalPrice(total);
		newOrder.setOrderDate(new Date());
		newOrder.setUser(UserService.getUserById(userId));

		Order savedOrder = orderRepo.save(newOrder);
		for (Cart cart : cartList) {
			cart.setOrder(savedOrder);
			cart.setPurchased(true);
			cartRepo.save(cart);
		}

		return savedOrder;

	}

	@Override
	public List<OrderInfo> getOrdersByDateRange(Date fromDate, Date toDate) {
		// TODO Auto-generated method stub

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(toDate);
		calendar.add(Calendar.DAY_OF_MONTH, 1);

		Date toDateInclusive = calendar.getTime();

		List<Order> findByDateBetweenOrders = orderRepo.findByOrderDateBetween(fromDate, toDateInclusive);

		List<OrderInfo> orderInfoList = new ArrayList<>();

		for (Order order : findByDateBetweenOrders) {

			User user = UserService.getUserById(order.getUser().getUserId());
			OrderInfo orderInfo = new OrderInfo(user, order, order.getOrderDate());
			orderInfoList.add(orderInfo);
		}

		return orderInfoList;
	}

}
