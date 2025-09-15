package com.DtoEntityIdProblem.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DtoEntityIdProblem.Service.UserService;
import com.DtoEntityIdProblem.dto.CreateUserRequest;
import com.DtoEntityIdProblem.dto.UserResponse;

import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService service;
 
	public UserController(UserService service) {
		super();
		this.service = service;
	}
	@PostMapping
	public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request){
		UserResponse created = service.create(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}
}