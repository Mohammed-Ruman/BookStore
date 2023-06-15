package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Cart;
import com.project.bookstore.Entity.Category;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Service.BookService;
import com.project.bookstore.Service.CartService;
import com.project.bookstore.Service.CategoryService;
import com.project.bookstore.Service.UserService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Override
	public Book addBook(String book) {
		// TODO Auto-generated method stub
		JSONObject bookJson = new JSONObject(book);

		List<Book> findAllByBookTitleAndAuthor = bookRepo.findAllByBookTitleAndAuthor(bookJson.getString("bookTitle"),
				bookJson.getString("author"));

		if (!findAllByBookTitleAndAuthor.isEmpty()) {
			throw new BadRequestException("This book is already added : Duplicate Addition");
		}

		else {

			List<Category> categories = new ArrayList<Category>();

			bookJson.getJSONArray("categories").forEach(cart -> {
				categories.add(categoryService.getCategoryById((Integer) cart));
			});
			
//			Book newBook = Book.builder().bookTitle(bookJson.getString("bookTitle")).author(bookJson.getString("author"))
//			.description(bookJson.getString("description")).price(bookJson.getDouble("price")).categories(categories).build();
//			return bookRepo.save(newBook);
			return null;
		}

	}

	@Override
	public Book getBookById(Integer bookId) {
		// TODO Auto-generated method stub
		return bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id :" + bookId));
	}

	@Override
	public List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String keywords) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (keywords == "") {
			return bookRepo.findAll(pageable).getContent();
		} else {
			return bookRepo.seachByTitle(keywords, pageable).getContent();
		}
	}

	@Override
	public List<User> findUserPurchasedBook(Integer bookId) {
		// TODO Auto-generated method stub

		return cartService.getCartByBookId(bookId).stream().map(cart -> userService.getUserById(cart.getUserId()))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteBook(Integer bookId) {
		// TODO Auto-generated method stub

		bookRepo.delete(getBookById(bookId));

	}

}
