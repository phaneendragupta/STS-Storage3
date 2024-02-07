package com.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.dto.SignInRequest;
import com.weatherapp.dto.SignUpRequest;
import com.weatherapp.exceptions.InvalidCredentialsException;
import com.weatherapp.exceptions.UserFoundException;
import com.weatherapp.security.JwtAuthResponse;
import com.weatherapp.services.AuthenticationServiceImpl;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationServiceImpl authService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> SignIn(@Valid @RequestBody SignInRequest request) throws Exception,InvalidCredentialsException{
//		System.out.println("Entering login");

		JwtAuthResponse response = authService.signIn(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/SignUp")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest request) throws UserFoundException {

		String response = authService.signUp(request);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
