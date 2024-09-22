package com.application.blog.service;

import java.util.Set;

import com.application.blog.payloads.CommentDto;

public interface CommentService {
	
	//get comment
	Set<CommentDto> getCommentsOfPost(Integer postId);
	//create comment
	CommentDto createComment(CommentDto commentDto,Integer userId, Integer postId);
	//update comment
	CommentDto updateComment(CommentDto commentDto, Integer commentId);
	//delete comment
	void deleteComment(Integer commentId);
}
