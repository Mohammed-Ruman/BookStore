package com.project.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	//add book
	@PostMapping("/")
	public ResponseEntity<Book> addBook(@RequestBody Book book){
		Book addBook = bookService.addBook(book);
		return new ResponseEntity<Book>(addBook,HttpStatus.CREATED);
	}
	
	//get book by id
	@GetMapping("/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Integer bookId){
		Book bookById = bookService.getBookById(bookId);
		return new ResponseEntity<Book>(bookById, HttpStatus.OK);
	}
	
	//get all books
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBook(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false)Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "2",required = false)Integer pageSize
			){
		List<Book> allBooks = this.bookService.getAllBooks(pageNumber, pageSize);
		
		return new ResponseEntity<List<Book>>(allBooks,HttpStatus.OK);
	}
	
	//search book by title
	@GetMapping("/search/{keywords}")
	public ResponseEntity<List<Book>> seachBook(@PathVariable String keywords){
		List<Book> seachBookByTitle = this.bookService.seachBookByTitle(keywords);
		return new ResponseEntity<List<Book>>(seachBookByTitle,HttpStatus.OK);
	}
}
