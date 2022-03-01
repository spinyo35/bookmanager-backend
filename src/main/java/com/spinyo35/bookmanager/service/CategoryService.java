package com.spinyo35.bookmanager.service;

import java.util.List;

import com.spinyo35.bookmanager.model.Category;

public interface CategoryService {
	Category addCategory(Category category);
	List<Category> getAllCategories();
	Category getCategoryById(Long id);
	Category updateCategory(Category book,Long id);
	void deleteCategory(Long id);
}
