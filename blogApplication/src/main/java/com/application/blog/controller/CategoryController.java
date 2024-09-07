package com.application.blog.controller;

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

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.CategoryDto;
import com.application.blog.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired 
	private CategoryService categoryService;
	
	//create category
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory,HttpStatus.CREATED);
	}
	
	//get all categories
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategories(){
		List<CategoryDto> categoryDtos=this.categoryService.getAllCategories();
		return ResponseEntity.ok(categoryDtos);
	}
	
	//get category by id
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer cid){
		CategoryDto categoryDto=this.categoryService.getCategoryById(cid);
		return ResponseEntity.ok(categoryDto);
	}
	
	//update category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		CategoryDto updatedCategoryDto=this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategoryDto);
	}
	
	//delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully !!",true),HttpStatus.OK);
	}
	
}
