package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;

	// add book
	@PostMapping
	public ResponseEntity<Object> addBook(@RequestBody String book) {

		return new ResponseEntity<Object>(bookService.addBook(book), HttpStatus.CREATED);
	}

	// get book by id
	@GetMapping("/{bookId}")
	public ResponseEntity<Object> getBookById(@PathVariable Integer bookId) {

		return new ResponseEntity<Object>(bookService.getBookById(bookId), HttpStatus.OK);
	}

	// get all books
	@GetMapping
	public ResponseEntity<Object> getAllBook(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value = "keywords", defaultValue = "", required = false) String keywords) {

		return new ResponseEntity<Object>(bookService.getAllBooks(pageNumber, pageSize, keywords), HttpStatus.OK);
	}

	// to find book purchased by how many user
	@GetMapping("/purchased/{bookId}")
	public ResponseEntity<Object> findBookByPurchased(@PathVariable Integer bookId) {

		return new ResponseEntity<Object>(bookService.findUserPurchasedBook(bookId), HttpStatus.OK);

	}

	// to delete a book by id
	@DeleteMapping("/{bookId}")
	public ResponseEntity<Object> deleteBook(@PathVariable Integer bookId) {
		if (bookService.deleteBook(bookId)) {
			return ResponseEntity.ok("success : Book deleted");
		}
		return null;
	}

	// find all books by categoryid
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<Object> getAllBooksByCategory(@PathVariable Integer categoryId) {

		return new ResponseEntity<Object>(bookService.getAllBooksByCategoryId(categoryId), HttpStatus.OK);
	}

	// update category of the book
	@PutMapping("/category/{categoryId}")
	public ResponseEntity<Object> updateAllBooksByCategory(@PathVariable Integer categoryId) {
		bookService.updateBookCategory(categoryId);
		return ResponseEntity.ok("success : done");
	}
}
