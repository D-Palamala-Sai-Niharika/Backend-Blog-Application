package com.application.blog.service;

import java.util.List;

import com.application.blog.payloads.CategoryDto;

public interface CategoryService {
	//create category
	CategoryDto createCategory(CategoryDto categoryDto);
	//get all categories
	List<CategoryDto> getAllCategories();
	//get category by id
	CategoryDto getCategoryById(Integer id);
	//update category
	CategoryDto updateCategory(CategoryDto categoryDto, Integer id);
	//delete category
	void deleteCategory(Integer id);
}
