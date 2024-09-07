package com.application.blog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name="users",
	uniqueConstraints=@UniqueConstraint(columnNames= {"email_address"})
)
@Getter
@Setter
@Builder //to add builder pattern for entity class
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=100,nullable=false)
	private String name;
	
	@Column(name="email_address",nullable=false)
	//@Column(name="email_address",nullable=false,unique=true)
	private String email;
	
	@Column
	private String password;
	
	private String about;

}
