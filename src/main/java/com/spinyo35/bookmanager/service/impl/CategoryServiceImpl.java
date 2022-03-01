package com.spinyo35.bookmanager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spinyo35.bookmanager.exception.ResourceNotFoundException;

import com.spinyo35.bookmanager.model.Category;
import com.spinyo35.bookmanager.repository.CategoryRepository;
import com.spinyo35.bookmanager.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository repository;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository repository) {
		super();
		this.repository = repository;
	}


	@Override
	public Category addCategory(Category category) {
		return repository.save(category);
	}


	@Override
	public List<Category> getAllCategories() {
		return repository.findAll();
	}


	@Override
	public Category getCategoryById(Long id) {
		return repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", id)
				);
	}


	@Override
	public Category updateCategory(Category category, Long id) {
		Category categoryFound = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", id)
				);
		
		categoryFound.setTitle(category.getTitle());
		
		return repository.save(categoryFound);
	}


	@Override
	public void deleteCategory(Long id) {
		repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Category", "Id", id)
				);
		repository.deleteById(id);
		
	}

}
