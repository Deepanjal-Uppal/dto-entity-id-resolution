package com.DtoEntityIdProblem.Service;

import com.DtoEntityIdProblem.dto.CreateUserRequest;
import com.DtoEntityIdProblem.dto.UserResponse;

public interface UserService {
	
	UserResponse create(CreateUserRequest request);

}
