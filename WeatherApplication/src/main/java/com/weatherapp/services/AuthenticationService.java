package com.weatherapp.services;

import com.weatherapp.dto.SignInRequest;
import com.weatherapp.dto.SignUpRequest;
import com.weatherapp.exceptions.InvalidCredentialsException;
import com.weatherapp.exceptions.UserFoundException;
import com.weatherapp.security.JwtAuthResponse;

public interface AuthenticationService {
	String signUp(SignUpRequest request) throws UserFoundException;

	JwtAuthResponse signIn(SignInRequest request) throws Exception,InvalidCredentialsException;
}
