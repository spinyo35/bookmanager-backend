package com.spinyo35.bookmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spinyo35.bookmanager.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
