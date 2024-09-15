package com.application.blog.service;

import java.time.LocalDateTime;
import java.util.List;

import com.application.blog.payloads.PostDto;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//get all posts
	List<PostDto> getAllPosts();
	//get post by post id
	PostDto getPostById(Integer postId);
	//get all posts of a user
	List<PostDto> getAllPostsOfUser(Integer userId);
	//get all posts in a category
	List<PostDto> getAllPostOfCategory(Integer categoryId);
	//search posts by title
	List<PostDto> searchPostsByTitle(String keyword);
	//find posts created between
	List<PostDto> getPostsCreatedBetween(LocalDateTime startDate,LocalDateTime endDate);
	//update a post
	PostDto updatePost(PostDto postDto, Integer postId);
	//delete a post
	void deletePost(Integer postId);
}
