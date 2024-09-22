//package com.application.blog.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//import com.application.blog.payloads.Employee;
//import com.application.blog.repository.EmployeeRepository;
//
//import jakarta.validation.Valid;
//
//@RestController
//public class EmployeeController {
//	
//	@Autowired
//	private EmployeeRepository empRepo;
//	
//	@PostMapping("/employee")
//	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee){
//		Employee createdEmployee =this.empRepo.save(employee);
//		return new ResponseEntity<Employee>(createdEmployee, HttpStatus.CREATED);
//	}
//
//}
