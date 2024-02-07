package com.weatherapp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.weatherapp.services.UserServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired(required = true)
	private UserServiceImpl userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		//1. get token from the request.
		String requestToken = request.getHeader("Authorization");

		//the request token will look like
		//Bearer

		String username = null;

		String token = null;

		if(requestToken!=null && requestToken.startsWith("Bearer")) {

			//actual token starts from the index 7
			token = requestToken.substring(7);

			//getting username from the token
			try {
			username = this.jwtTokenUtil.extractUsername(token);
//			System.out.println(username);
			}
			catch(IllegalArgumentException e) {
				System.out.println("Unable to get Jwt Token");
			}
			catch(ExpiredJwtException e) {
				System.out.println("Jwt Token has Expired");
			}
			catch(MalformedJwtException e){
				System.out.println("Invalid Jwt");
			}
		}
		else {
			System.out.println("Jwt token does not begin with bearer");
		}

		//Once we get the token, we need to validate using JwtTokenHelper
//		System.out.println(username);
//		System.out.println(SecurityContextHolder.getContext().getAuthentication());

		if(token!= null && SecurityContextHolder.getContext().getAuthentication()== null) {
			//If the condition is true, then validate the token using token and username;
			// we need to get the userdetails by using loadByUsername;
//			System.out.println("entering here");
			UserDetails userDetails = this.userService.loadUserByUsername(username);

			if(this.jwtTokenUtil.validateToken(token, userDetails)) {
				//then we need to do the authentication

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				//set details to the above generated token
				//setDeatils method requires an object of webAuthenticationDetailsSource, so we are generating using the servlet request.
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("Invalid Jwt Token");
			}
		}


		filterChain.doFilter(request, response);
	}

}
