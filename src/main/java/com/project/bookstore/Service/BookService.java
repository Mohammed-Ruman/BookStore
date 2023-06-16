package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.User;

public interface BookService {

	// add book
	Book addBook(String book);

	// get book by id
	Book getBookById(Integer bookId);

	// get all book
	List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String keywords);

	// find book purchased by user
	List<User> findUserPurchasedBook(Integer bookId);
	
	//to delete the book
	boolean deleteBook(Integer bookId);

}
