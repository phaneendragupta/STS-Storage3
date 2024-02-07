package com.weatherapp.services;

import com.weatherapp.exceptions.UserNotFoundException;

public interface AdminService  {

	String deleteUser(String userEmail) throws UserNotFoundException;
}
