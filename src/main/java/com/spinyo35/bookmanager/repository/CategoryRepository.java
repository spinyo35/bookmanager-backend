package com.spinyo35.bookmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spinyo35.bookmanager.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
