package com.weatherapp.services;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weatherapp.dto.UserDto;
import com.weatherapp.entities.User;
import com.weatherapp.exceptions.UserNotFoundException;
import com.weatherapp.repositories.UserRepository;


@Service
public class AdminServiceImpl implements AdminService {
	private static Logger logger = LogManager.getLogger();

	@Autowired
	private UserRepository userRepo;



	@Override
	public String deleteUser(String userEmail) throws UserNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Finding the optional object using userId from the database");
		Optional<User> user = userRepo.findUserByuserEmail(userEmail);
		logger.info("checking for the presence of user object");
		if (user.isPresent()) {
			logger.info("User has been deleted");
			userRepo.delete(user.get());
		} else {
			throw new UserNotFoundException("User not found with user id " + userEmail);
		}
		logger.info("Returning deleted user object");
		User deletedUser = user.get();
		UserDto nUserDto = new UserDto();

		nUserDto.setUserEmail(deletedUser.getUserEmail());
		nUserDto.setName(deletedUser.getUsername());
		//return nUserDto;
		return "User Deleted SuccessFully: "+ nUserDto.getName();

	}

}
