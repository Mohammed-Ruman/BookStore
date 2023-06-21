package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Author;

public interface AuthorService {

	// add author
	Author addAuthor(Author author);

	// get author by id
	Author getAuthorById(Integer authorId);

	// get all authors
	List<Author> getAllAuthors(Integer pageNumber, Integer pageSize, String keywords);

	// delete author
	void deleteAuthorById(Integer authorId);

	// update author
	Author updateAuthorById(Author author, Integer authorId);

}
