package com.weatherapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {

	@Email
	@NotNull
	private String email;

	@NotNull
	private String password;
}
