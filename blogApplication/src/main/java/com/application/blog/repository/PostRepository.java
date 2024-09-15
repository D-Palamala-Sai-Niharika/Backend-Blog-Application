package com.application.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.blog.entity.Category;
import com.application.blog.entity.Post;
import com.application.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	//All posts of a user
	List<Post> findByUser(User user);
	//All posts in a category
	List<Post> findByCategory(Category category);
	//Search - All posts by title
	List<Post> findByTitle(String title);
	//All posts created between
	List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
	
}

