package com.weatherapp.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.weatherapp.dto.SignInRequest;
import com.weatherapp.dto.SignUpRequest;
import com.weatherapp.entities.Role;
import com.weatherapp.entities.User;
import com.weatherapp.exceptions.InvalidCredentialsException;
import com.weatherapp.exceptions.UserFoundException;
import com.weatherapp.repositories.UserRepository;
import com.weatherapp.security.JwtAuthResponse;
import com.weatherapp.security.JwtTokenUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	UserServiceImpl userService;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public String signUp(SignUpRequest request) throws UserFoundException {
		logger.info("checking for the presence of user object with the given name");
		Optional<User> userOpt = userRepo.findUserByuserName(request.getUsername());
		Optional<User> userOpt1 = userRepo.findUserByuserEmail(request.getEmail());


		if (userOpt.isPresent()) {
			throw new UserFoundException(
					"Given user name " + request.getUsername() + " present already! Choose different one");
		} else if (userOpt1.isPresent()) {
			throw new UserFoundException(
					"Given email address " + request.getEmail() + " present already! Choose different one");
		}
		else {
		User user = new User();
		user.setUserName(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setUserEmail(request.getEmail());
		Role role1 = new Role();
		role1.setRole_name("USER");
		Set<Role> roles = new HashSet<>();
		roles.add(role1);
		user.setRoles(roles);


		userRepo.save(user);

		return "User created Successfully";
		}
	}

	@Override
	public JwtAuthResponse signIn(SignInRequest request) throws Exception,InvalidCredentialsException {
		try {
			UsernamePasswordAuthenticationToken uptoken = new UsernamePasswordAuthenticationToken(request.getEmail(),
					request.getPassword());
			Authentication auth = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
			UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
			String token = jwtTokenUtil.generateToken(userDetails.getUsername());

			JwtAuthResponse response = new JwtAuthResponse();
			response.setToken(token);
			response.setAuthorities(userDetails.getAuthorities());
			response.setUsername(request.getEmail());
			return response;

		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("Invalid Username or password");
		}
	}

}
