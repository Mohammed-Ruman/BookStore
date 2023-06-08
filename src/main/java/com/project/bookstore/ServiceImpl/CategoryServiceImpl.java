package com.project.bookstore.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Category;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.CategoryRepo;
import com.project.bookstore.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.save(category);
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId));
	}

	@Override
	public List<Category> getAllCategory(Integer pageNumber, Integer pageSize) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Category> categories = this.categoryRepo.findAll(pageable);

		List<Category> contentCategories = categories.getContent();

		return contentCategories;
	}

	@Override
	public List<Category> searchCategory(String keyword) {
		// TODO Auto-generated method stub

		List<Category> searchByCategory = categoryRepo.searchByCategory(keyword);

		return searchByCategory;
	}

}
