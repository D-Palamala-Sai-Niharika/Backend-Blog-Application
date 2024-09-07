package com.application.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
