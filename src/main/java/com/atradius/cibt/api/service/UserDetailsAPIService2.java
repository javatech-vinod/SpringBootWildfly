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



@FeignClient(url="${user.service}",name="SERVICE-CLIENT")
public interface UserDetailsAPIService2 {

	@GetMapping("/port")
	public String getMicro2Instance();
	
	
	
	
	
}
