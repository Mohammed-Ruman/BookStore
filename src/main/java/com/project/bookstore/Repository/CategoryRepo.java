package com.project.bookstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
	
	@Query("select c from Category c where c.categoryName like %:key%")
	List<Category> searchByCategory( String key);
		
}
