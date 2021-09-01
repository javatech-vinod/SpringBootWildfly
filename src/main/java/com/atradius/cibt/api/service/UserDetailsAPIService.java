package com.atradius.cibt.api.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.atradius.cibt.api.dto.UserResponse;
import com.atradius.cibt.api.exception.ResourceNotFoundException;



@FeignClient(url="${user.url}",name="USER-CLIENT")
public interface UserDetailsAPIService {

	@GetMapping("/posts")
	public List<UserResponse> getUsers();
	
	
	@PostMapping("/posts")
	public String saveUsers(UserResponse user);
	
	@PutMapping("/posts/{id}")
	public UserResponse updateUsers(@PathVariable("id")Integer id) throws ResourceNotFoundException;
	
	@DeleteMapping("/posts/{id}")
	public UserResponse deleteUsers(@PathVariable("id")String id) throws ResourceNotFoundException;
	
	
	
}
