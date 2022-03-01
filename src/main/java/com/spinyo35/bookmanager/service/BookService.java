package com.spinyo35.bookmanager.service;

import java.util.List;

import com.spinyo35.bookmanager.model.Book;

public interface BookService {
	Book addBook(Book book);
	List<Book> getAllBooks();
	List<Book> getAllBooksByCategory(Long categoryId);
	Book getBookById(Long id);
	Book updateBook(Book book,Long id);
	void deleteBook(Long id);
}
