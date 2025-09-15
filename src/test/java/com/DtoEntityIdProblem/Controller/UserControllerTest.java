package com.DtoEntityIdProblem.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.DtoEntityIdProblem.Service.UserService;
import com.DtoEntityIdProblem.dto.CreateUserRequest;
import com.DtoEntityIdProblem.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

//In a WebMvcTest you're testing the controller only.
//so you mock the service to return a UserResponse with a non null id
//If u forget the mock the service (or mock it to return an is of null)
//they'll see id: null in the response -
//this is a common source of confusion
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test

	void createUser_return201AndBodyWithGeneratedId() throws Exception {
		CreateUserRequest req = new CreateUserRequest("ABC", "abc@example.com");
		UserResponse resp = new UserResponse(1L, "ABC", "abc@example.com");
		Mockito.when(userService.create(Mockito.any())).thenReturn(resp);
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("ABC")))
				.andExpect(jsonPath("$.email", is("abc@example.com")));
	}
}

//Why students see id = null
//They create DTO with id in the request: Don't. The DB should generate it.
//They create DTO with id in the constructor for POST.
//Dont use a request DTO without id.
//In tests(WebMvc Test), they mock the service to return a DTO with id = null
//Make the mock return a UserResponse with a non-null id.
//In integration/service tests, they build an Entity and forget that id is only set
//after save(). Always assert after save.
