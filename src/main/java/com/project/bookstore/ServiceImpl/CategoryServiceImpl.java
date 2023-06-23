package com.project.bookstore.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Category;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.CategoryRepo;
import com.project.bookstore.Service.BookService;
import com.project.bookstore.Service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private BookService bookService;

	@Override
	public Category addCategory(Category category) {
		// TODO Auto-generated method stub
		Optional<Category> findByCategoryName = categoryRepo.findByCategoryName(category.getCategoryName());
		if(findByCategoryName.isPresent()) {
			throw new BadRequestException("Error : This category is already added");
		}
		return categoryRepo.save(category);
	}

	@Override
	public Category getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id : " + categoryId));
	}

	@Override
	public List<Category> getAllCategory(Integer pageNumber, Integer pageSize, String keyword) {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return  keyword.isBlank()? categoryRepo.findAll(pageable).getContent() : categoryRepo.searchByCategory(keyword, pageable).getContent();
	}

	@Override
	public boolean deleteCategoryById(Integer categoryId) {

		categoryRepo.findById(categoryId).map(category -> {
			bookService.updateBookCategory(categoryId);
			categoryRepo.delete(category);
			return true;
		}).orElseThrow(() -> new ResourceNotFoundException("Category not found with category id: " + categoryId));
		return true;
	}

	@Override
	public Category updateCategoryById(Integer categoryId, Category category) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(categoryId).map(cat->{
			cat.setCategoryName(category.getCategoryName()==null? cat.getCategoryName():category.getCategoryName());
			cat.setCategoryDescription(category.getCategoryDescription()==null?cat.getCategoryDescription():category.getCategoryDescription());
			return categoryRepo.save(cat);
		}).orElseThrow(()-> new ResourceNotFoundException("Error : Category not found with id : " + categoryId));
	}

}
