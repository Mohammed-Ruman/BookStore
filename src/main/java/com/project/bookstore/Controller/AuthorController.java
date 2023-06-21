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

import com.project.bookstore.Entity.Author;
import com.project.bookstore.Service.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	// add new author
	@PostMapping
	public ResponseEntity<Object> addNewAuthor(@RequestBody Author author) {
		return new ResponseEntity<Object>(authorService.addAuthor(author), HttpStatus.CREATED);
	}

	// get all author
	@GetMapping
	public ResponseEntity<Object> getAllAuthor(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
			@RequestParam(value = "keywords", defaultValue = "", required = false) String keywords) {
		return new ResponseEntity<Object>(authorService.getAllAuthors(pageNumber, pageSize, keywords), HttpStatus.OK);
	}

	// get author by id
	@GetMapping("/{authorId}")
	public ResponseEntity<Object> getAuthorById(@PathVariable Integer authorId) {
		return ResponseEntity.ok(authorService.getAuthorById(authorId));
	}

	// delete author
	@DeleteMapping
	public ResponseEntity<Object> deleteAuthorById(@RequestParam(name = "authorId") Integer authorId) {
		authorService.deleteAuthorById(authorId);
		return ResponseEntity.ok("Success : Author Deleted");
	}

	// update author
	@PutMapping
	public ResponseEntity<Object> updateAuthorById(@RequestBody Author author,
			@RequestParam(name = "authorId") Integer authorId) {
		return new ResponseEntity<Object>(authorService.updateAuthorById(author, authorId), HttpStatus.OK);
	}

}
