package com.application.blog.service.Impl;

import java.time.LocalDateTime;
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
		//pages - getContent(), getNumber(), getSize(), getNumberOfElements(), getTotalElements(), getTotalPages(), isLast()
		//https://www.perplexity.ai/search/getmapping-value-posts-produce-BY09FVgvSmKuz9WLxcqV1Q
		
//		Sort sort=null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}else {
//			sort=Sort.by(sortBy).descending();
//		}
		
		Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pagination=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> posts=this.postRepo.findAll(pagination);
		List<Post> getPosts=posts.getContent();
		List<PostDto> postDtos=getPosts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setNoOfElements(posts.getNumberOfElements());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostsOfUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostOfCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		List<Post> posts=this.postRepo.findByCategory(category);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}
	
	@Override
	public List<PostDto> searchPostsByTitle(String keyword) {
		//Derived Custom Method
		//List<Post> posts=this.postRepo.findByTitleContaining(keyword);
		//JPQL
		//List<Post> posts=this.postRepo.getPostsByTitleContaining("%"+keyword+"%");
		//List<Post> posts=this.postRepo.getPostsByTitleContaining(keyword);
		//Native Query
		List<Post> posts=this.postRepo.getPostsByTitleContaining(keyword);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()); 
		return postDtos;
	}
	
	@Override
	public List<PostDto> getPostsCreatedBetween(LocalDateTime startDate, LocalDateTime endDate) {
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
		post.setImage(postDto.getImage());
		Post updatedPost=this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		this.postRepo.delete(post);
	}

}
