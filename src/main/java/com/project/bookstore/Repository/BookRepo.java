package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.Author;
import com.project.bookstore.Entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer> {

	List<Book> findByBookTitleContaining(String keyword);

	@Query("select b from Book b where b.bookTitle like %:key% AND b.isDeleted = :isDeleted")
	Page<Book> seachByTitle(@Param("key") String key, Pageable pageable, @Param("isDeleted") boolean isDeleted);

	List<Book> findAllByBookTitleAndAuthorAndIsDeleted(String title, Author author, boolean isDeleted);

	@Query("select b from Book b where b.isDeleted = :isDeleted")
	Page<Book> findAllByIsDeleted(@Param("isDeleted") boolean isDeleted, Pageable pageable);

	@Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
	List<Book> findByCategoryId(@Param("categoryId") Integer categoryId);
}
