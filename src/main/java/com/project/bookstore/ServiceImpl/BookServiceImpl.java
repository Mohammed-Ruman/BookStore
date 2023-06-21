package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.bookstore.Entity.Author;
import com.project.bookstore.Entity.Book;
import com.project.bookstore.Entity.Category;
import com.project.bookstore.Entity.User;
import com.project.bookstore.Exception.BadRequestException;
import com.project.bookstore.Exception.ResourceNotFoundException;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Service.AuthorService;
import com.project.bookstore.Service.BookService;
import com.project.bookstore.Service.CartService;
import com.project.bookstore.Service.CategoryService;
import com.project.bookstore.Service.UserService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepo bookRepo;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorService authorService;

	@Override
	public Book addBook(String book) {
		// TODO Auto-generated method stub
		JSONObject bookJson = new JSONObject(book);

		Author authorById = authorService.getAuthorById(bookJson.getInt("authorId"));

		List<Book> findAllByBookTitleAndAuthor = bookRepo
				.findAllByBookTitleAndAuthorAndIsDeleted(bookJson.getString("bookTitle"), authorById, false);

		if (!findAllByBookTitleAndAuthor.isEmpty()) {
			throw new BadRequestException("This book is already added : Duplicate Addition");
		}

		else {

			List<Category> categories = new ArrayList<Category>();

			bookJson.getJSONArray("categories").forEach(cat -> {
				categories.add(categoryService.getCategoryById((Integer) cat));
			});

			Book newBook = Book.builder().bookTitle(bookJson.getString("bookTitle")).author(authorById)
					.description(bookJson.getString("description")).isDeleted(false).price(bookJson.getDouble("price"))
					.categories(categories).build();

			return bookRepo.save(newBook);

		}

	}

	@Override
	public Book getBookById(Integer bookId) {
		// TODO Auto-generated method stub
		return bookRepo.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id :" + bookId));
	}

	@Override
	public List<Book> getAllBooks(Integer pageNumber, Integer pageSize, String keywords) {
		// TODO Auto-generated method stub

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		if (keywords.isBlank()) {
			return bookRepo.findAllByIsDeleted(false, pageable).getContent();
		} else {
			return bookRepo.seachByTitle(keywords, pageable, false).getContent();
		}
	}

	@Override
	public List<User> findUserPurchasedBook(Integer bookId) {
		// TODO Auto-generated method stub

		return cartService.getCartByBookId(bookId).stream().map(cart -> userService.getUserById(cart.getUserId()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean deleteBook(Integer bookId) {
		// TODO Auto-generated method stub

		Book bookById = getBookById(bookId);
		if (bookById.isDeleted() == false) {
			bookById.setDeleted(true);
			bookRepo.save(bookById);
			return true;
		} else {
			throw new BadRequestException("Error: Book with given id: " + bookId + " already deleted!");
		}
	}

	@Override
	public List<Book> getAllBooksByCategoryId(Integer categoryId) {
		// TODO Auto-generated method stub
		List<Book> findByCategoryId = bookRepo.findByCategoryId(categoryId);

		return findByCategoryId;
	}

	@Override
	public void updateBookCategory(Integer categoryId) {
		// TODO Auto-generated method stub

		getAllBooksByCategoryId(categoryId).forEach(book -> {
			book.getCategories().removeIf(category -> category.getCategoryId() == categoryId);
			bookRepo.save(book);
		});

	}

}
