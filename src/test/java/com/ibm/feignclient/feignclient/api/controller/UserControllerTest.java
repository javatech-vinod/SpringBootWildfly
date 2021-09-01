package com.ibm.feignclient.feignclient.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atradius.cibt.api.controller.UserController;
import com.atradius.cibt.api.dto.UserResponse;
import com.atradius.cibt.api.exception.ResourceNotFoundException;
import com.atradius.cibt.api.service.UserDetailsAPIService;
import com.atradius.cibt.api.validator.UserResponseValidation;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserDetailsAPIService feignAPI;

	@Mock
	UserResponseValidation validator;

	@Mock
	UserResponse userResponse;

	@Test
	@DisplayName("should return user list")
	public void getAllUser() {
		List<UserResponse> userResponse = Arrays.asList(new UserResponse());
		when(feignAPI.getUsers()).thenReturn(userResponse);
		ResponseEntity<List<UserResponse>> userListResponseEntity = userController.getUser();
		assertThat(userListResponseEntity.getBody()).isNotNull();
		assertThat(userListResponseEntity.getBody()).isNotEmpty();
	}

	@Test
	@DisplayName("should return user response")
	public void saveUser() {
		String actualResult = "saved successfully";
		doNothing().when(validator).validate(userResponse);
		// when(userResponse.toString()).thenReturn(actualResult);
		when(feignAPI.saveUsers(userResponse)).thenReturn(actualResult);
		ResponseEntity<String> expectedString = userController.saveUser(userResponse);

		assertThat(expectedString).isNotNull();

	}

	@Test
	@DisplayName("should return user response object")
	public void updateUser() {
		Integer actualResult = 1;
		when(feignAPI.updateUsers(actualResult)).thenReturn(userResponse);
		UserResponse expectedString = userController.updateUser(actualResult);
		assertThat(expectedString).isNotNull();
		

	}

	@Test
	@DisplayName("should return delete user")
	public void deleteUser() {
		String actualResult = "1";
		when(feignAPI.deleteUsers(actualResult)).thenReturn(userResponse);
		ResponseEntity<UserResponse> response = userController.deleteUser(actualResult);
		assertThat(response.getBody()).isNotNull();
	}
	
	@Test
	@DisplayName("should throw exception on delete user")
	public void shouldThrowExceptionOndeleteUser() {
		String msg="USER DATA NOT FOUND";
		String id="1";
		ResourceNotFoundException resourceNotFoundException=new ResourceNotFoundException(msg);
		when(feignAPI.deleteUsers(id)).thenThrow(resourceNotFoundException);
		Assertions.assertThrows(ResourceNotFoundException.class, ()->userController.deleteUser(id));
	}

}
