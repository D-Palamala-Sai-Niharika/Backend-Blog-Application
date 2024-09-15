package com.application.blog.service;

import java.util.List;

import com.application.blog.payloads.PostDto;
import com.application.blog.payloads.PostResponse;

public interface PostService {
	
	//create post
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	//get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	//get post by post id
	PostDto getPostById(Integer postId);
	//get all posts of a user
	//List<PostDto> getAllPostsOfUser(Integer userId);
	PostResponse getAllPostsOfUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	//get all posts in a category
	//List<PostDto> getAllPostOfCategory(Integer categoryId);
	PostResponse getAllPostOfCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	//search posts by title
	List<PostDto> searchPostsByTitle(String keyword);
	//find posts created between
	List<PostDto> getPostsCreatedBetween(String startDate,String endDate);
	//update a post
	PostDto updatePost(PostDto postDto, Integer postId);
	//delete a post
	void deletePost(Integer postId);
}
