package com.project.bookstore.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.bookstore.Entity.Author;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
	
}
