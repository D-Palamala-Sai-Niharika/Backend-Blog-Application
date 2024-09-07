package com.application.blog.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.blog.entity.Category;
import com.application.blog.payloads.CategoryDto;
import com.application.blog.exception.ResourceNotFoundException;
import com.application.blog.repository.CategoryRepository;
import com.application.blog.service.CategoryService;

@Service
public class CategoryImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category=this.modelMapper.map(categoryDto, Category.class);
		Category createCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(createCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos=categories.stream().map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) {
		Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		Category updatedCategory=this.categoryRepo.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","Id",id));
		this.categoryRepo.delete(category);
	}

}
