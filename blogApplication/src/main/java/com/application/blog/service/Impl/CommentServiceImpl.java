package com.application.blog.service.Impl;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.blog.entity.Comment;
import com.application.blog.exception.ResourceNotFoundException;
import com.application.blog.payloads.CommentDto;
import com.application.blog.repository.CommentRepository;
import com.application.blog.repository.PostRepository;
import com.application.blog.repository.UserRepository;
import com.application.blog.service.CommentService;
import com.application.blog.entity.User;
import com.application.blog.entity.Post;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PostRepository postRepo;
	
	@Override
	public CommentDto createComment(CommentDto commentDto,Integer userId, Integer postId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setCreatedAt(LocalDateTime.now());
		comment.setPost(post);
		comment.setUser(user);
		Comment createdComment=this.commentRepo.save(comment);
		return this.modelMapper.map(createdComment, CommentDto.class);
	}
	
	@Override
	public Set<CommentDto> getCommentsOfPost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id",postId));
		Set<Comment> comments=this.commentRepo.findByPost(post);
		Set<CommentDto> commentDtos=comments.stream().map((comment)->this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet());
		return commentDtos;
	}


	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		comment.setComment(commentDto.getComment());
		Comment updatedComment=this.commentRepo.save(comment);
		return this.modelMapper.map(updatedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		this.commentRepo.delete(comment);
	}

}
