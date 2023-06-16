package com.project.bookstore.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.Author;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
	
	@Query("select a from Author a where a.authorName like %:keys%")
	Page<Author> searchByAuthorName(@Param("keys")String keywords, Pageable pageable);
}
