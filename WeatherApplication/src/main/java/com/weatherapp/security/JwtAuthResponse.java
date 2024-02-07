package com.weatherapp.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private Collection<? extends GrantedAuthority> authorities;
	private String username;
	private String token;
}
