package com.application.blog.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.blog.entity.Comment;
import com.application.blog.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Set<Comment> findByPost(Post post);
}
