package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Author;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.AuthorRepo;
import com.project.bookstore.Service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService {

	@Autowired
	private AuthorRepo authorRepo;

	@Override
	public Author addAuthor(Author author) {
		// TODO Auto-generated method stub
		if(authorRepo.findByAuthorNameAndEmail(author.getAuthorName(), author.getEmail()).isPresent()) {
			throw new BadRequestException("Error : Author already present");
		}
		return authorRepo.save(author);
	}

	@Override
	public Author getAuthorById(Integer authorId) {
		// TODO Auto-generated method stub
		return authorRepo.findById(authorId)
				.orElseThrow(() -> new ResourceNotFoundException("Author not found with id " + authorId));
	}

	@Override
	public List<Author> getAllAuthors(Integer pageNumber, Integer pageSize, String keywords) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return keywords.isBlank()? authorRepo.findAll(pageable).getContent():authorRepo.searchByAuthorName(keywords, pageable).getContent();

	}

	@Override
	public void deleteAuthorById(Integer authorId) {
		// TODO Auto-generated method stub
		authorRepo.delete(getAuthorById(authorId));
	}

	@Override
	public Author updateAuthorById(Author author, Integer authorId) {
		// TODO Auto-generated method stub
		return authorRepo.findById(authorId).map(existingAuthor -> {
			existingAuthor.setAuthorName(author.getAuthorName()==null? existingAuthor.getAuthorName():author.getAuthorName());
			existingAuthor.setEmail(author.getEmail() == null ? existingAuthor.getEmail() : author.getEmail());
			return authorRepo.save(existingAuthor);
		}).orElseThrow(() -> new ResourceNotFoundException("Error :Author with id: " + authorId + " not found"));
	}

}
