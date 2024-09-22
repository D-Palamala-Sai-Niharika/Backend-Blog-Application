package com.application.blog.payloads;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Integer postId;
	
	@NotNull
	@Size(min=3,max=150,message="Title must be between 3 to 150 characters !!")
	private String title;

	private String description;
	
	@Size(max=100000, message="Content can not exceed 10000 characters !")
	private String content;                 // title, desc, content,img taken from postdto
	
	private String coverImage;
	
	private LocalDateTime createdAt;        // set in backend , so optional
	private LocalDateTime modifiedAt;
	
	private CategoryDto category;         // categoryDto, userDto taken from pathVariable
	private UserDto user;                 // Dto does not have post info so no loop of execution
	
	private Set<CommentDto> comments;
	
	// Set of user IDs who liked this post
    private Set<Integer> likedByUserIds;

}
