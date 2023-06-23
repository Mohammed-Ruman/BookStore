package com.project.bookstore.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.bookstore.Entity.Category;
import com.project.bookstore.Service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Object> addCategory(@RequestBody Category category) {
		return new ResponseEntity<Object>(categoryService.addCategory(category), HttpStatus.CREATED);
	}

	@GetMapping("/{categoryId}")
	public ResponseEntity<Object> getCategoryById(@PathVariable Integer categoryId) {
		return new ResponseEntity<Object>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<Object> getAllCategory(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value="keywords", defaultValue = "",required = false)String keywords) {
		return new ResponseEntity<Object>(categoryService.getAllCategory(pageNumber, pageSize,keywords), HttpStatus.OK);
	}

	// delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<Object> deleteCategory(@PathVariable Integer categoryId) {
		boolean isDeleted = categoryService.deleteCategoryById(categoryId);
		return isDeleted ? ResponseEntity.ok("Success : Category deleted") : null;
	}
	
	//update category by id
	@PutMapping
	public ResponseEntity<Object> updateCategory(@RequestParam(value = "categoryId") Integer categoryId, @RequestBody Category category) {
		return ResponseEntity.ok(categoryService.updateCategoryById(categoryId, category));
	}
	
	
}
