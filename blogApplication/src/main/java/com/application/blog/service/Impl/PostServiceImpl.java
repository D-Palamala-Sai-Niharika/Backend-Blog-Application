package com.application.blog.service.Impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.application.blog.payloads.PostDto;
import com.application.blog.payloads.PostResponse;
import com.application.blog.exception.ResourceNotFoundException;
import com.application.blog.entity.Post;
import com.application.blog.entity.User;
import com.application.blog.entity.Category;
import com.application.blog.repository.CategoryRepository;
import com.application.blog.repository.PostRepository;
import com.application.blog.repository.UserRepository;
import com.application.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		// user, category from url path variable, createdAt,modifiedAt set in backend, remaining fields - title, desc, content, imgUrl in dto from user
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setUser(user);
		post.setCategory(category);
		post.setCreatedAt(LocalDateTime.now());
		post.setModifiedAt(LocalDateTime.now());
		Post createdPost=this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		//Page<T> pages=repo.findAll(Pageable pageable);
		//Sort sort=Sort.by(columnName).ascending();
		//pageable=PageRequest.of(pageNumber,pageSize,sort)
		//first sorts based on param and then returns details with page no & page size mentioned
		//argument input - pageable, returns - Page , page -  getContent(), getNumber(), getSize(), getNumberOfElements(), getTotalElements(), getTotalPages(), isLast()
		//https://www.perplexity.ai/search/getmapping-value-posts-produce-BY09FVgvSmKuz9WLxcqV1Q
		Pageable pageable=this.pagingAndSorting(pageNumber, pageSize, sortBy, sortDir);
		Page<Post> posts=this.postRepo.findAll(pageable);
		PostResponse postResponse=this.createPostResponse(posts);
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public PostResponse getAllPostsOfUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		Pageable pageable=this.pagingAndSorting(pageNumber, pageSize, sortBy, sortDir);
		Page<Post> posts=this.postRepo.findByUser(user,pageable);
		PostResponse postResponse=this.createPostResponse(posts);
		return postResponse;
	}

	@Override
	public PostResponse getAllPostOfCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		Pageable pageable=this.pagingAndSorting(pageNumber, pageSize, sortBy, sortDir);
		Page<Post> posts=this.postRepo.findByCategory(category,pageable);
		PostResponse postResponse=this.createPostResponse(posts);
		return postResponse;
	}
	
	@Override
	public List<PostDto> searchPostsByTitle(String keyword) {
		//Derived Custom Method
		//List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		//JPQL
		//List<Post> posts=this.postRepo.getPostsByTitleContaining("%"+keyword+"%");
		//List<Post> posts=this.postRepo.getPostsByTitleContaining(keyword);
		//Native Query
		List<Post> posts=this.postRepo.findByTitleContainingIgnoreCase(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		return postDtos;
	}
	
	@Override
	public List<PostDto> getPostsCreatedBetween(String startDateStr, String endDateStr) {
		//http://localhost:8081/api/filter/posts?startDate=2024-09-13T15:06:29&endDate=2024-09-14T16:06:29
		LocalDateTime startDate = null;
		LocalDateTime endDate = null;

		// Define a custom date format if needed
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		// Parse the startDate and endDate strings
		try {
		    if (startDateStr != null && !startDateStr.isEmpty()) {
		        //startDate = LocalDateTime.parse(startDateStr, formatter);
		        startDate = LocalDateTime.parse(startDateStr);
		    }
		    if (endDateStr != null && !endDateStr.isEmpty()) {
		        //endDate = LocalDateTime.parse(endDateStr, formatter);
		    	 endDate = LocalDateTime.parse(endDateStr);
		    }
		} catch (DateTimeParseException e) {
		        throw new IllegalArgumentException("Invalid date format. Please use 'yyyy-MM-dd'T'HH:mm:ss'");
		}
		List<Post> posts=this.postRepo.findByCreatedAtBetween(startDate, endDate);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		return postDtos;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCoverImage(postDto.getCoverImage());
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		this.postRepo.delete(post);
	}
	
	public Pageable pagingAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
//		Sort sort=null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pagination=PageRequest.of(pageNumber, pageSize,sort);
		return pagination;
	}

	public PostResponse createPostResponse(Page<Post> posts) {
		PostResponse postResponse=new PostResponse();
		List<Post> postsOfUser=posts.getContent();
		List<PostDto> postDtos=postsOfUser.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setNoOfElements(posts.getNumberOfElements());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}

}
