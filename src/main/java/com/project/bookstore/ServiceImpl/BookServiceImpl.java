package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

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

		if (findAllByBookTitleAndAuthor != null) {
			throw new BadRequestException("This book is already added : Duplicate Addition");
		}

		else {

			Book newBook = new Book();

			newBook.setBookTitle(bookJson.getString("bookTitle"));
			newBook.setAuthor(bookJson.getString("author"));
			newBook.setDescription(bookJson.getString("description"));
			newBook.setPrice(bookJson.getDouble("price"));

			JSONArray catArray = bookJson.getJSONArray("categories");

			List<Category> categories = new ArrayList<Category>();

			// Iterate over the categories array and create Category objects
			for (int i = 0; i < catArray.length(); i++) {
				Integer catId = (Integer) catArray.get(i);
				// Category category = new Category();
				Category category = categoryService.getCategoryById(catId);
				categories.add(category);
			}

			// Set the categories list on the Book object
			newBook.setCategories(categories);

			return bookRepo.save(newBook);
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

			Page<Book> bookPage = this.bookRepo.findAll(pageable);

			List<Book> books = bookPage.getContent();

			return books;
		} else {
			Page<Book> byTitlePage = this.bookRepo.seachByTitle(keywords, pageable);

			List<Book> books = byTitlePage.getContent();
			return books;
		}
	}

//	@Override
//	public List<Book> seachBookByTitle(String keyword) {
//		// TODO Auto-generated method stub
//		
//		List<Book> seachByTitle = this.bookRepo.seachByTitle(keyword);
//		
//		
//		
//		return seachByTitle;
//	}

	@Override
	public List<User> findUserPurchasedBook(Integer bookId) {
		// TODO Auto-generated method stub

		List<Cart> cartByBookId = cartService.getCartByBookId(bookId);

		List<User> purchasedUsers = new ArrayList<>();

		cartByBookId.forEach(cart -> {
			User userById = userService.getUserById(cart.getUserId());
			purchasedUsers.add(userById);
		});

		return purchasedUsers;
	}

}
