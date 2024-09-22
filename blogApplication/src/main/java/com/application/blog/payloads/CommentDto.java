package com.application.blog.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	private Integer commentId;  //set in backend
	@NotNull(message="Enter valid comment!!")
	private String comment;  // only comment taken from client/requestbody
	private LocalDateTime createdAt; //set in backend
	private Integer postId;         // taken in pathvariable and set using postrepo
	private Integer userId;         // taken in pathvariable and set using userrepo
}
