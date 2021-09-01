package com.atradius.cibt.api.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
	private static final long serialVersionUID = 1L;
	private Date timestamp;
	private String message;
	private String details;
	
	
	

}
