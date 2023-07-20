package com.project.bookstore.ServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.project.bookstore.Payload.BookApiResponse;
import com.project.bookstore.Repository.BookRepo;
import com.project.bookstore.Repository.OrderRepo;
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

	@Autowired
	private OrderRepo orderRepo;

	@Override
	public Book addBook(String book) {
		// TODO Auto-generated method stub
		JSONObject bookJson = new JSONObject(book);

		Author authorById = authorService.getAuthorById(bookJson.getInt("authorId"));

		List<Book> findAllByBookTitleAndAuthor = bookRepo
				.findAllByBookTitleAndAuthorAndIsDeleted(bookJson.getString("bookTitle"), authorById, false);

		if (!findAllByBookTitleAndAuthor.isEmpty()) {
			throw new BadRequestException("This book is already added : Duplicate Addition");
		}else if(authorById.getIsDisabled()) {
			throw new BadRequestException("Error : Author is disabled");
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
			return filterBookByAuthor( bookRepo.findAllByIsDeleted(false, pageable).getContent());
		} else {
			return filterBookByAuthor( bookRepo.seachByTitle(keywords, pageable, false).getContent());
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
		return bookRepo.findByCategoryId(categoryId);
	}

	@Override
	public void updateBookCategory(Integer categoryId) {
		// TODO Auto-generated method stub

		getAllBooksByCategoryId(categoryId).forEach(book -> {
			book.getCategories().removeIf(category -> category.getCategoryId() == categoryId);
			bookRepo.save(book);
		});

	}

	

	@Override
	public Book updateBook(String book, Integer bookId) {
		// TODO Auto-generated method stub
		JSONObject updateBook = new JSONObject(book);

		return bookRepo.findByBookIdAndIsDeleted(bookId, false).map(existingBook -> {
			existingBook.setBookTitle(updateBook.getString("bookTitle") == null ? existingBook.getBookTitle()
					: updateBook.getString("bookTitle"));
			if (updateBook.has("authorId") && existingBook.getAuthor().getAuthorId() != updateBook.getInt("authorId")) {
				existingBook.setAuthor(authorService.getAuthorById(updateBook.getInt("authorId")));
			}
			existingBook.setDescription(updateBook.has("description") ? updateBook.getString("description")
					: existingBook.getDescription());
			existingBook.setPrice(updateBook.has("price") ? updateBook.getDouble("price") : existingBook.getPrice());
			if (updateBook.has("categories")) {
				List<Category> categories = new ArrayList<Category>();
				updateBook.getJSONArray("categories").forEach(cat -> {
					categories.add(categoryService.getCategoryById((Integer) cat));
				});
				existingBook.setCategories(categories);
			}
			return bookRepo.save(existingBook);

		}).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

	}
 
	@Override
	public BookApiResponse getMostSoldBook() {
		// TODO Auto-generated method stub
		Map<Integer, Integer> soldBookMap = new HashMap<>();

		orderRepo.findAll().forEach(order -> {
			order.getCartItems().forEach(cart -> {
				if (soldBookMap.containsKey(cart.getBookId())) {
					soldBookMap.put(cart.getBookId(), soldBookMap.get(cart.getBookId()) + cart.getQuantity());
				} else {
					soldBookMap.put(cart.getBookId(), cart.getQuantity());
				}
			});
		});
		Integer highestSoldBookId = soldBookMap.entrySet().stream().max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse(null);

		Book bookById = getBookById(highestSoldBookId);

		return BookApiResponse.builder().bookTitle(bookById.getBookTitle())
				.authorName(bookById.getAuthor().getAuthorName()).description(bookById.getDescription())
				.numberOfCopiesSold(soldBookMap.get(highestSoldBookId)).build();
	}
	
	@Override
	public BookApiResponse getLeastSoldBook() {
		// TODO Auto-generated method stub
		Map<Integer, Integer> soldBookMap = new HashMap<>();

		orderRepo.findAll().forEach(order -> {
			order.getCartItems().forEach(cart -> {
				if (soldBookMap.containsKey(cart.getBookId())) {
					soldBookMap.put(cart.getBookId(), soldBookMap.get(cart.getBookId()) + cart.getQuantity());
				} else {
					soldBookMap.put(cart.getBookId(), cart.getQuantity());
				}
			});
		});
		Integer lowestSoldBookId = soldBookMap.entrySet().stream().min(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey).orElse(null);
		Book bookById = getBookById(lowestSoldBookId);
		System.out.println(soldBookMap.toString());
		
		return BookApiResponse.builder().bookTitle(bookById.getBookTitle())
				.authorName(bookById.getAuthor().getAuthorName()).description(bookById.getDescription())
				.numberOfCopiesSold(soldBookMap.get(lowestSoldBookId)).build();
	}
	
	public List<Book> filterBookByAuthor(List<Book> books){
		return books.stream().filter(book->!book.getAuthor().getIsDisabled()).toList();
	}

}
