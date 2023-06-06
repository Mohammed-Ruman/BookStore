package com.project.bookstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Category;
import com.project.bookstore.Service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		Category addCategory = categoryService.addCategory(category);
		return new ResponseEntity<Category>(addCategory,HttpStatus.CREATED);
	}
	
	@GetMapping("/{categoryId}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Integer categoryId){
		Category categoryById = categoryService.getCategoryById(categoryId);
		return new ResponseEntity<Category>(categoryById, HttpStatus.OK);
	}
	
	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getAllCategory(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize
			){
		return new ResponseEntity<List<Category>>(categoryService.getAllCategory(pageNumber,pageSize),HttpStatus.OK);
	}
	
	//search category
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<Category>> searchCategory(@PathVariable String keyword){
		List<Category> searchCategory = categoryService.searchCategory(keyword);
		return new ResponseEntity<List<Category>>(searchCategory, HttpStatus.OK);
	}
}
