package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer>{
	
	List<Book> findByBookTitleContaining(String keyword);
	
	@Query("select b from Book b where b.bookTitle like %:key%")
	List<Book> seachByTitle(@Param("key") String key);
}
