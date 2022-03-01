package com.spinyo35.bookmanager.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spinyo35.bookmanager.exception.ResourceNotFoundException;
import com.spinyo35.bookmanager.model.Book;

import com.spinyo35.bookmanager.repository.BookRepository;
import com.spinyo35.bookmanager.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	BookRepository repository;
	
	@Autowired
	public BookServiceImpl(BookRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public Book addBook(Book book) {
		return repository.save(book);
	}


	@Override
	public List<Book> getAllBooks() {
		return repository.findAll();
	}


	@Override
	public List<Book> getAllBooksByCategory(Long categoryId) {
		return repository.findAll().stream().filter(b -> b.getCategoryId() == categoryId).collect(Collectors.toList());
	}

	@Override
	public Book getBookById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book", "Id", id)
				);
	}
	
	@Override
	public Book updateBook(Book book, Long id) {
		Book bookFound = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book", "Id", id)
				);
		
		bookFound.setCategoryId(book.getCategoryId());
		bookFound.setDescription(book.getDescription());
		bookFound.setIsAvailable(book.getIsAvailable());
		bookFound.setPrice(book.getPrice());
		bookFound.setTitle(book.getTitle());
		
		return repository.save(bookFound);
	}


	@Override
	public void deleteBook(Long id) {
		repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Book", "Id", id)
				);
		repository.deleteById(id);
	}

}
