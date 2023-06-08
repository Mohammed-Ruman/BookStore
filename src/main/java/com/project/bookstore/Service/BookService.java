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

	// search book by title

	// List<Book> seachBookByTitle(String keyword);

	// find book purchased by user
	List<User> findUserPurchasedBook(Integer bookId);

}
