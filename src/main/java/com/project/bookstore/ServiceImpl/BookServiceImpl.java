package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Repository.CategoryRepo;
import com.project.bookstore.Service.BookService;
import com.project.bookstore.Service.CartService;
import com.project.bookstore.Service.CategoryService;
import com.project.bookstore.Service.UserService;

@Service
public class BookServiceImpl implements BookService{

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
		JSONObject bookJson=new JSONObject(book);
		Book newBook=new Book();
		
		newBook.setBookTitle(bookJson.getString("bookTitle"));
		newBook.setAuthor(bookJson.getString("author"));
		newBook.setDescription(bookJson.getString("description"));
		newBook.setPrice(bookJson.getDouble("price"));
		
		JSONArray array = bookJson.getJSONArray("categories");
		
		List<Category> categories = new ArrayList<Category>();

		// Iterate over the categories array and create Category objects
		for (int i = 0; i < array.length(); i++) {
		    Integer catId = (Integer) array.get(i);
		    //Category category = new Category();
		    Category category = categoryService.getCategoryById(catId);
		    categories.add(category);
		}
		
		
		// Set the categories list on the Book object
		newBook.setCategories(categories);
		
		return bookRepo.save(newBook);
	}

	@Override
	public Book getBookById(Integer bookId) {
		// TODO Auto-generated method stub
		return bookRepo.findById(bookId).orElseThrow(()-> new ResourceNotFoundException("Book not found with id :" +bookId));
	}

	@Override
	public List<Book> getAllBooks(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		
		Page<Book> bookPage = this.bookRepo.findAll(pageable);
		
		List<Book> books = bookPage.getContent();
		//System.out.println(books);
		return books;
	}

	@Override
	public List<Book> seachBookByTitle(String keyword) {
		// TODO Auto-generated method stub
		
		List<Book> seachByTitle = this.bookRepo.seachByTitle(keyword);
		
		
		
		return seachByTitle;
	}

	@Override
	public List<User> findUserPurchasedBook(Integer bookId) {
		// TODO Auto-generated method stub
		
		List<Cart> cartByBookId = cartService.getCartByBookId(bookId);
		
		List<User> purchasedUsers=new ArrayList<>();
		
		cartByBookId.forEach(cart -> {
			User userById = userService.getUserById(cart.getUserId());
			purchasedUsers.add(userById);
		});
		
		return purchasedUsers;
	}
	

}
