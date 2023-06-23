package com.project.bookstore.Service;

import java.util.List;

import com.project.bookstore.Entity.Category;

public interface CategoryService {

	// add Category
	Category addCategory(Category category);

	// get Category by id
	Category getCategoryById(Integer categoryId);

	// get all category
	List<Category> getAllCategory(Integer pageNumber, Integer pageSize,String keyword);

	// delete category
	boolean deleteCategoryById(Integer categoryId);

	// update category
	Category updateCategoryById(Integer categoryId, Category category);
}
