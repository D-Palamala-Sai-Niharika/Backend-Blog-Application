package com.application.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Integer id;
	
	@NotNull
	@Size(min=3,max=100,message="Name must be atleast 3 characters !")
	private String name;
	
	@NotNull(message="Email is must !!")
	@Email(message="Enter a valid email address")
	private String email;
	
	@NotNull
	@Pattern(regexp="^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",message="Incorrect Password")
	private String password;
//	This regex will enforce these rules:
//	At least one upper case English letter, (?=.*?[A-Z])
//	At least one lower case English letter, (?=.*?[a-z])
//	At least one digit, (?=.*?[0-9])
//	At least one special character, (?=.*?[#?!@$%^&*-])
//	Minimum eight in length .{8,} (with the anchors)
	
	@NotNull
	@Size(min=10, max=255, message="About must have minimum of 10 characters and can exceed till 255 characters")
	private String about;
}
