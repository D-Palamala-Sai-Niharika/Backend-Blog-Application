//package com.application.blog.payloads;
//
//
//import com.application.blog.validation.ValidEmployeeType;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotNull;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name="employees")
//public class Employee {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private Integer empId;
//	
//	@NotNull(message="Name cannot be empty !!")
//	private String name;
//	
//	@Email(message="Please enter valid email id")
//	private String email;
//	
//	@ValidEmployeeType
//	private String empType;
//	
//}
