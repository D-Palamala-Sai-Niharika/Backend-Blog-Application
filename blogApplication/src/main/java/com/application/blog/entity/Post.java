package com.application.blog.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="posts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="post_title",length=150,nullable=false)
	private String title;
	
	@Column(name="post_description")
	private String description;
	
	@Column(length=100000)
	private String content;
	
	private String coverImage;
	
	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="category_id",referencedColumnName="categoryId")
	private Category category;
	
	
}
