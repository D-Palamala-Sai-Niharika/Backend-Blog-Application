package com.application.blog.controller;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.PostDto;
import com.application.blog.service.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//create post
	@PostMapping(value="/user/{userId}/category/{categoryId}/post",consumes="application/json")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createdPostDto=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	//get all posts - use same controller/api with different requestparams
	//pagination - pageSize, pageNumber
	//sorting - asc, desc
	//searching - search posts by title - keyword
	//get posts created between - startDate, endDate
	//ex:http://localhost:8081/posts?pazeSize=5&pageNo=2&sortBy=title
	@GetMapping(value="/posts",produces="application/json")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> postDtos=this.postService.getAllPosts();
		return ResponseEntity.ok(postDtos);
	}
	
	//get post by post id
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto=this.postService.getPostById(postId);
		return ResponseEntity.ok(postDto);
	}

	//get all posts of a user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Integer userId){
		List<PostDto> postDtos=this.postService.getAllPostsOfUser(userId);
		return ResponseEntity.ok(postDtos);
	}
	
	//get all posts in a category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsOfCategory(@PathVariable Integer categoryId){
		List<PostDto> postDtos=this.postService.getAllPostOfCategory(categoryId);
		return ResponseEntity.ok(postDtos);
	}

	//update a post
	@PutMapping(value="/post/{postId}",consumes="application/json")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPostDto=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}

	//delete a post
	@DeleteMapping(value="post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted Successfully",true),HttpStatus.OK);
	}
}
