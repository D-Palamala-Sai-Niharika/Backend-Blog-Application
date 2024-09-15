package com.application.blog.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.application.blog.entity.Category;
import com.application.blog.entity.Post;
import com.application.blog.entity.User;
import com.application.blog.constants.QueryConstants;

public interface PostRepository extends JpaRepository<Post, Integer> {
	
	//All posts of a user
	List<Post> findByUser(User user);
	//All posts in a category
	List<Post> findByCategory(Category category);
	//Search - All posts by title - Derived Custom Method, JPQL, Native Query
	//Derived Custom Method
	//List<Post> findByTitleContaining(String title);
	//JPQL - based on class name nd properties
	//@Query("select p from Post p where p.title like %:keyword%")
	//List<Post> getPostsByTitleContaining(@Param("keyword") String keyword);
	//Native Query - based on table name nd column names)
	//@Query(value="select * from posts where post_title like %:keyword%",nativeQuery=true)
	@Query(value=QueryConstants.SEARCH_POSTS_BY_TITLE_CONTAINING,nativeQuery=true)
	List<Post> getPostsByTitleContaining(@Param("keyword") String keyword);
	//All posts created between
	List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	
	
}

