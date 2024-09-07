package com.application.blog.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotNull
	@Size(min=3,max=100,message="Title must be between 3 to 100 chanaracters !!")
	private String title;
	
	@NotNull
	@Size(min=10,message="Description must be atleast 10 characters")
	private String description;
}
