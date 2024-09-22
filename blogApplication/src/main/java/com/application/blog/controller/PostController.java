package com.application.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.PostDto;
import com.application.blog.payloads.PostResponse;
import com.application.blog.service.FileService;
import com.application.blog.service.PostService;
import com.application.blog.constants.AppConstants;
import com.application.blog.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${blog.app.images.path}")
	private String path;
	
	//create post
	@PostMapping(value="/user/{userId}/category/{categoryId}/post",consumes="application/json")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createdPostDto=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPostDto,HttpStatus.CREATED);
	}
	
	//get all posts - use same controller/api with different requestparams
	//pagination - pageSize, pageNumber
	//sorting - column, asc/desc
	//ex:http://localhost:8081/posts?pazeSize=5&pageNo=2&sortBy=title
	@GetMapping(value="/posts",produces="application/json")
	public ResponseEntity<PostResponse> getAllPosts(
			 // @RequestParam(value="pageNumber", defaultValue="0", required=false) Integer pageNumber, 
			  @RequestParam(value="pageNumber", defaultValue=AppConstants.DEFAULT_PAGE_NUMBER, required=false) Integer pageNumber,  //static variables are accessed without object creation with class name
			  @RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) Integer pageSize,
			  @RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_POSTS_SORT_BY, required=false)  String sortBy, //default value based on column name postId/title/...
			  @RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
			){
		PostResponse postResponse=this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}
	
	//searching - search posts by title - keyword
	@GetMapping("/search/{keyword}/posts")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable("keyword") String searchKeyword){
		List<PostDto> postDtos=this.postService.searchPostsByTitle(searchKeyword);
		return ResponseEntity.ok(postDtos);
	}
	
	//get posts created between - startDate, endDate
	@GetMapping("/filter/posts")
	public ResponseEntity<List<PostDto>> filterPostsByCreatedAtBetween(
			@RequestParam(value="startDate",required=false) String startDate,
			@RequestParam(value="endDate",required=false) String endDate
		){
		
		List<PostDto> postDtos=this.postService.getPostsCreatedBetween(startDate, endDate);
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
	public ResponseEntity<PostResponse> getAllPostsOfUser(
			@PathVariable Integer userId,
			@RequestParam(value="pageNumber", defaultValue=AppConstants.DEFAULT_PAGE_NUMBER, required=false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_POSTS_SORT_BY, required=false)  String sortBy, 
			@RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
		){
		PostResponse postResponse=this.postService.getAllPostsOfUser(userId, pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}
	
	//get all posts in a category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<PostResponse> getAllPostsOfCategory(
			@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber", defaultValue=AppConstants.DEFAULT_PAGE_NUMBER, required=false) Integer pageNumber, 
			@RequestParam(value="pageSize", defaultValue=AppConstants.DEFAULT_PAGE_SIZE, required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue=AppConstants.DEFAULT_POSTS_SORT_BY, required=false)  String sortBy,
			@RequestParam(value="sortDir", defaultValue=AppConstants.DEFAULT_SORT_DIR, required=false) String sortDir
		){
		PostResponse postResponse=this.postService.getAllPostOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
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
	
	//upload image
	@PostMapping(value="/post/{postId}/uploadImage",  consumes = "multipart/form-data", produces = MediaType.APPLICATION_JSON_VALUE)  // body - form data - image - file
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
		String fileName=this.fileService.uploadImage(image, path);
		postDto.setCoverImage(fileName);
		PostDto updatePostDto=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
	}
	
	//serve image
	@GetMapping(value="/image/{fileName}", produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public void serveImage(@PathVariable String fileName, HttpServletResponse response) throws IOException{
		InputStream resource=this.fileService.serveImage(fileName, path);
		String fileExtension=StringUtils.getFilenameExtension(fileName);
		if(fileExtension.equalsIgnoreCase("png")) {
			response.setContentType(MediaType.IMAGE_PNG_VALUE);
		}else if(fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		}
		StreamUtils.copy(resource,response.getOutputStream());
	}

	// Toggle like for a specific post 
	@PostMapping("/user/{userId}/post/{postId}/like") 
	public ResponseEntity<Map<String,Object>> toggleLike(@PathVariable Integer postId, @PathVariable Integer userId) { 
		Map<String,Object> response=new HashMap<>(); 
		this.postService.toggleLike(postId,userId); 
		response.put("message","Like toggled successfully"); 
		response.put("likesCount",postService.getLikesCount(postId)); 
		return ResponseEntity.ok(response); 
	} 
	
}
