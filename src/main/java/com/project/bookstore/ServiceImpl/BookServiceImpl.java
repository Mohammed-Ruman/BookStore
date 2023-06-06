package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Service.BookService;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepo bookRepo;
	
	
	@Override
	public Book addBook(Book book) {
		// TODO Auto-generated method stub
		
		return bookRepo.save(book);
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
		System.out.println(books);
		return books;
	}

	@Override
	public List<Book> seachBookByTitle(String keyword) {
		// TODO Auto-generated method stub
		
		List<Book> seachByTitle = this.bookRepo.seachByTitle("%"+keyword+"%");
		
		
		
		return seachByTitle;
	}
	

}
