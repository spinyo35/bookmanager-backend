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

import com.spinyo35.bookmanager.model.Category;
import com.spinyo35.bookmanager.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	private CategoryService service;

	@Autowired
	public CategoryController(CategoryService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/categories")
	public ResponseEntity<Category> addCategory(@RequestBody Category category){
		return new ResponseEntity<Category>(service.addCategory(category), HttpStatus.CREATED);
	}
	
	@GetMapping("/categories")
	public List<Category> getAllCategories(){
		return service.getAllCategories();
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id){
		return new ResponseEntity<Category>(service.getCategoryById(id),HttpStatus.OK);
	}
	
	@PutMapping("/categories/{id}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category,@PathVariable("id") Long id){
		return new ResponseEntity<Category>(service.updateCategory(category,id), HttpStatus.OK);
	} 
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id){
		service.deleteCategory(id);
		return new ResponseEntity<String>(String.format("Category with id:'%s' successfully deleted.", id),HttpStatus.OK);
	}
}
