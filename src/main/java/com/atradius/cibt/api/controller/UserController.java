package com.atradius.cibt.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atradius.cibt.api.dto.UserResponse;
import com.atradius.cibt.api.exception.ResourceNotFoundException;
import com.atradius.cibt.api.service.UserDetailsAPIService;
import com.atradius.cibt.api.service.UserDetailsAPIService2;
import com.atradius.cibt.api.validator.UserResponseValidation;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * @author 002A4W744
 *
 */
@RestController
@AllArgsConstructor
@Log4j2
public class UserController {


	@Autowired
	private UserDetailsAPIService feignClient;
	
	@Autowired
	private UserDetailsAPIService2 feignClient1;
	
	@Autowired
	private UserResponseValidation userResponseValidator;

	/***
	 * This method reads data from third party api using getAllusers()
	 * @returns List<UserResponse>
	 */
	//@RateLimiter(name="default")//10s--100
	//@Retry(name="sample-api",fallbackMethod = "userServiceFallBack")
	@GetMapping("/getAllUsers")
//	@CircuitBreaker(fallbackMethod = "getMicro2InstanceFallback", name = "") 
	public ResponseEntity<List<UserResponse>> getUser() {
		//log.info("inside normal method");
		log.info("Request received for all users");	
		return ResponseEntity.ok(feignClient.getUsers());
	}

	public ResponseEntity<UserResponse> userServiceFallBack(){
		return ResponseEntity.ok(new UserResponse());
		
	}
		
	/**This method takes User object as input from JSON/XML using
	 * @param user
	 * @RequestBody and returns ResponseEntity<UserResponse>. call feignClient.saveUser(obj)
	 */
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody UserResponse user) {
		userResponseValidator.validate(user);
		log.info("Request received for saving user for user:", user);
		String response=feignClient.saveUsers(user);
		return new ResponseEntity<String>(response,HttpStatus.CREATED);
	}

	/**
	 * This method update data from third party api using updateUser()
	 * @param id
	 * @return UserResponse
	 */
	@PutMapping("/updateUser/{id}")
	public UserResponse updateUser(@PathVariable("id") Integer id) {
		try {
			log.info("Request received for saving user for user:", id);
			return feignClient.updateUsers(id);
		}catch (ResourceNotFoundException exception) {
			log.error("Unable to update resource");
			throw new ResourceNotFoundException("Unable to update resource"+id);
		}
		
	}

	/**
	 * This method delete user data from third party api using deleteUser()
	 * @param id
	 * @return ResponseEntity<UserResponse>
	 */
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity<UserResponse> deleteUser(@PathVariable("id") String id) {
		try {
			log.info("Request received for Deleting user for Id:", id);
			UserResponse response=feignClient.deleteUsers(id);
			return new ResponseEntity<UserResponse>(response,HttpStatus.OK);
		}catch(ResourceNotFoundException exception) {
			log.error("user resource not found");
			throw new ResourceNotFoundException("user resource not found"+id);
		}
		
	}
	
	/*
	 * @GetMapping("/getmicro2")
	 * 
	 * @CircuitBreaker(name = "orderService", fallbackMethod =
	 * "getMicro2InstanceFallback") //@CircuitBreaker(name =
	 * "userService",fallbackMethod = "getMicro2InstanceFallback" ) public
	 * ResponseEntity<String> getMicro2Instance() { String
	 * response=feignClient1.getMicro2Instance(); return new
	 * ResponseEntity<String>(response,HttpStatus.OK);
	 * 
	 * }
	 */
	
	  @GetMapping("/getmicro2")
	  
	 @HystrixCommand(fallbackMethod = "getMicro2InstanceFallback")
	  //@CircuitBreaker(name = "userService",fallbackMethod ="getMicro2InstanceFallback" ) 
	  public ResponseEntity<String>getMicro2Instance() { 
		  String response=feignClient1.getMicro2Instance();
	  return new ResponseEntity<String>(response,HttpStatus.OK);
	  
	  }
	 
	/*
	 * @GetMapping("/getmicro2")
	 * 
	 * @HystrixCommand(fallbackMethod = "getMicro2InstanceFallback")
	 * //@CircuitBreaker(name = "userService",fallbackMethod =
	 * "getMicro2InstanceFallback" ) public ResponseEntity<String>
	 * getMicro2Instance() {
	 * 
	 * String url ="http://localhost:8081/microservice1/port";
	 * System.out.println("inside normal method");
	 * 
	 * String response = new RestTemplate().getForObject(url, String.class); return
	 * new ResponseEntity<String>(response,HttpStatus.OK);
	 * 
	 * }
	 */
	 
	  
	  public ResponseEntity<String> getMicro2InstanceFallback() {
	  System.out.println("inside fallback method"); 
	  log.info("Inside fallback");
	  
	  return new ResponseEntity<String>("Microservice1 is down!!",HttpStatus.OK); 
	  }
	 

}
