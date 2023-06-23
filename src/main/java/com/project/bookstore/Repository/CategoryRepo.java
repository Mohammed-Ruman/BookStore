package com.project.bookstore.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.bookstore.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.categoryName like %:key%")
	Page<Category> searchByCategory(String key, Pageable pageable);
	
	Optional<Category> findByCategoryName(String categoryName);
	 

}
