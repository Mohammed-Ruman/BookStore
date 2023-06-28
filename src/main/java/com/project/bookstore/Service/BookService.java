package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Payload.BookApiResponse;

public interface BookService {

	// add book
	Book addBook(String book);
	
	//update book
	Book updateBook(String book, Integer bookId);

	// get book by id
	Book getBookById(Integer bookId);

	// get all book
	List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String keywords);

	// find book purchased by user
	List<User> findUserPurchasedBook(Integer bookId);

	// to delete the book
	boolean deleteBook(Integer bookId);

	// to get all books by category id
	List<Book> getAllBooksByCategoryId(Integer categoryId);

	// update book category
	void updateBookCategory(Integer categoryId);
	
	//most sold book
	BookApiResponse getMostSoldBook();
	
	//least sold book
	BookApiResponse getLeastSoldBook();

}
