package com.spinyo35.bookmanager.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.spinyo35.bookmanager.client.TransactionRestClient;
import com.spinyo35.bookmanager.exception.ResourceNotFoundException;
import com.spinyo35.bookmanager.model.Book;

import com.spinyo35.bookmanager.service.BookService;
import com.spinyo35.bookmanager.service.CategoryService;

@RestController
@RequestMapping("/api")
public class BookController {
	private BookService service;
	private CategoryService categoryService;
	private TransactionRestClient transaction;

	@Autowired
	public BookController(BookService service, TransactionRestClient transaction, CategoryService categoryService) {
		this.service = service;
		this.transaction = transaction;
		this.categoryService = categoryService;
	}

	@PostMapping("/books")
	public ResponseEntity<String> addBook(@RequestBody Book book) {
		try {
			categoryService.getCategoryById(book.getCategoryId());
			return new ResponseEntity<String>(service.addBook(book).toString(), HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<String>(String.format("Failed to add new Book["+book.toString()+"] : %s",ex.toString()),HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/books")
	public List<Book> getAllBooks() {
		return service.getAllBooks();
	}

	@GetMapping("/categorybooks/{cid}")
	public List<Book> getAllBooksByCategory(@PathVariable("cid") Long categoryId) {
		return service.getAllBooksByCategory(categoryId);
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getCategoryById(@PathVariable("id") Long id) {
		return new ResponseEntity<Book>(service.getBookById(id), HttpStatus.OK);
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<String> updateBook(@RequestBody Book book, @PathVariable("id") Long id) {
		
		try {
			categoryService.getCategoryById(book.getCategoryId());

			return new ResponseEntity<String>(service.updateBook(book, id).toString(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<String>(String.format("Failed to update Book["+book.toString()+"] : %s",ex.toString()),HttpStatus.NOT_ACCEPTABLE);
		}
		
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long bookId) {
		service.deleteBook(bookId);
		return new ResponseEntity<String>(String.format("Book with id:'%s' successfully deleted.", bookId),
				HttpStatus.OK);
	}

	// /api/purchase/1/1
	@PostMapping("/purchase/{bookId}/{card}")
	public ResponseEntity<String> purchaseBook(@PathVariable("bookId") Long bookId, @PathVariable("card") int card) {
		try {
			Book targetBook = this.service.getBookById(bookId);
			if (targetBook.getIsAvailable() == true) {
				// purchase the book
				String transactionOutcome = transaction.purchaseBook(bookId, targetBook.getPrice(), card);

				if (transactionOutcome.equals("SUCCESS")) {
					if (transaction.getResponseCode().equals("000")) {
						// mark book as bought
						targetBook.setIsAvailable(false);
						this.service.updateBook(targetBook, bookId);
						return new ResponseEntity<String>(
								String.format("Book: %s bought successfully.", targetBook.getTitle()), HttpStatus.OK);
					}
				} else {
					return new ResponseEntity<String>(
							String.format("Book purchase failed: Reason (%s).", transactionOutcome),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}

			} else {
				return new ResponseEntity<String>(String
						.format("Book: %s with id=%d is no longer in stock! Please check available books.", targetBook.getTitle(),targetBook.getId()),
						HttpStatus.OK);
			}
		} catch (ResourceNotFoundException rnfe) {
			return new ResponseEntity<String>(rnfe.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(String.format("Book purchase failed: Reason (%s:%s)",
				transaction.getResponseCode(), transaction.getResponseDescription()), HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
