package com.atradius.cibt.api.validator;

import org.springframework.stereotype.Component;

import com.atradius.cibt.api.dto.UserResponse;
import com.atradius.cibt.api.exception.ValidationException;

@Component
public class UserResponseValidation {

	public void validate(UserResponse user) {

		if(user==null)
			throw new ValidationException("Input request user object is null");
		
	}
	
}
