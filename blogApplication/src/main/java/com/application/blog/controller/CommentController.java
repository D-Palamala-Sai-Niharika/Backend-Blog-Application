package com.application.blog.controller;

import java.util.Set;

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
import org.springframework.web.bind.annotation.RestController;

import com.application.blog.payloads.ApiResponse;
import com.application.blog.payloads.CommentDto;
import com.application.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/user/{userId}/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable Integer userId, @PathVariable Integer postId){
		CommentDto createdCommentDto=this.commentService.createComment(commentDto,userId,postId);
		return new ResponseEntity<CommentDto>(createdCommentDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/comment/{commentId}")
	public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentDto commentDto,@PathVariable Integer commentId){
		CommentDto updatedCommentDto=this.commentService.updateComment(commentDto,commentId);
		return new ResponseEntity<CommentDto>(updatedCommentDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment deleted Successfully !!",true),HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}/comments")
	public ResponseEntity<Set<CommentDto>> getCommentsOfPost(@PathVariable Integer postId){
		Set<CommentDto> comments=this.commentService.getCommentsOfPost(postId);
		return new ResponseEntity<Set<CommentDto>>(comments,HttpStatus.OK);
	}
}
