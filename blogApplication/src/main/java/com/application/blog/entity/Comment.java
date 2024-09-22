package com.application.blog.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer commentId;
	
	@Column(length=200,nullable=false)
	private String comment;
	
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="post_id",referencedColumnName="postId")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	//MySQL Workbench->Database->Reverse Engineering->local host->generate automatic ER Diagram
}
