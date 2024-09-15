package com.application.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
	private List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private Integer noOfElements;
	private Long totalElements;
	private Integer totalPages;
	private boolean isLast;
}
