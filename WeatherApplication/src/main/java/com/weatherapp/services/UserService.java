package com.weatherapp.services;

import java.util.List;

import com.weatherapp.dto.UserDto;
import com.weatherapp.entities.User;
import com.weatherapp.exceptions.UserNotFoundException;

public interface UserService {
	UserDto createUser(User user);

	UserDto updateUserById(String userEmail,UserDto userDto) throws UserNotFoundException;

	UserDto getUserById(Integer userId) throws UserNotFoundException;

	UserDto getUserByEmail(String email) throws UserNotFoundException;

	UserDto getUserByName(String name) throws UserNotFoundException;

	List<UserDto> getAllUsers();


	String updateUserPassword(String userEmail, String newPassword) throws UserNotFoundException;

	//String deleteUser(Integer userId) throws UserNotFoundException;


}
