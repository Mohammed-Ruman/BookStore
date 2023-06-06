package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Book;

public interface BookService {

	//add book
	Book addBook(Book book);
	
	//get book by id
	Book getBookById(Integer bookId);
	
	
	//get all book
	List<Book> getAllBooks(Integer pageNumber, Integer pageSize);
	
	//search book by title
	List<Book> seachBookByTitle(String keyword);
	
}
