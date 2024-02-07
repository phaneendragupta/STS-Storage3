package com.weatherapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.weatherapp.dto.UserDto;
import com.weatherapp.entities.User;
import com.weatherapp.exceptions.UserNotFoundException;
import com.weatherapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private static Logger logger = LogManager.getLogger();


	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

//	@Autowired
//    private LocationRepository locationRepository;


	@Override
	public UserDto createUser(User user) {
		logger.info("Creating user");
		userRepo.save(user);
		UserDto u = new UserDto();
		u.setName(user.getUserName());
		u.setUserEmail(user.getUserEmail());
		return u;
	}

	@Override
	public UserDto updateUserById(String userEmail, UserDto userdto) throws UserNotFoundException {
		// TODO Auto-generated method stub
		logger.info("Finding the optional object using userId from the database");
		Optional<User> user = userRepo.findUserByuserEmail(userEmail);
		logger.info("checking for the presence of user object");
			if (user.isPresent()) {
				logger.info("Saving the updated details of the user");
				User newUser = user.get();

				ModelMapper mapper = new ModelMapper();
				mapper.map(userdto,newUser);
				userRepo.save(newUser);
				UserDto userd = modelMapper.map(newUser, UserDto.class);
				return userd;
			}
			else {
			// TODO Auto-generated catch block
			throw new UserNotFoundException("User not found with user id " + userEmail);

			}
	}



	@Override
	public UserDto getUserById(Integer userId) throws UserNotFoundException {
		logger.info("Getting the optional object of type user based on the userId from the database");
		Optional<User> user = userRepo.findById(userId);
		logger.info("Checking for the presence of user object ");
		if (user.isPresent()) {
			logger.info("User Object returned successfully");
			User nuser = user.get();
			UserDto userdto = new UserDto();
			userdto.setUserEmail(nuser.getUserEmail());
			userdto.setName(nuser.getUsername());
			return userdto;
		}
		else {
			throw new UserNotFoundException("User not found with user id " + userId);
		}

	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepo.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for (User n : userList) {
			UserDto user = modelMapper.map(n, UserDto.class);
			userDtoList.add(user);

		}
		return userDtoList;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserDetails u = this.userRepo.findUserByuserEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));

		return u;
	}

	@Override
	public UserDto getUserByEmail(String email) throws UserNotFoundException {
		logger.info("Getting the optional object of type user based on the user Email from the database");
		Optional<User> user = userRepo.findUserByuserEmail(email);
		logger.info("Checking for the presence of user object ");
		if (user.isPresent()) {
			logger.info("User Object returned successfully");
			User nuser = user.get();
			UserDto userdto = new UserDto();
		userdto.setUserEmail(nuser.getUserEmail());
			userdto.setName(nuser.getUserName());
			return userdto;
		}
		else {
				throw new UserNotFoundException("User not found with user email " + email);
		}
	}

	@Override
	public String updateUserPassword(String userEmail, String newPassword) throws UserNotFoundException {
		// TODO Auto-generated method stublogger.info("Getting the optional object using userId provided");
		Optional<User> user = userRepo.findUserByuserEmail(userEmail);
		logger.info("checking for the presence of user object in the container");
		if (user.isPresent()) {
			User dbUser = user.get();
			logger.info("updating and saving the updated password in the database");
			dbUser.setPassword(newPassword);
			userRepo.save(dbUser);
			return "Password Updated Successfully";
		} else {
			// if User not found in the database then it will throw exception.
			throw new UserNotFoundException("User not found with user id " + userEmail);

		}
	}

	@Override
	public UserDto getUserByName(String name) throws UserNotFoundException {
		logger.info("Getting the optional object of type user based on the username from the database");
		Optional<User> user = userRepo.findUserByuserName(name);
		logger.info("Checking for the presence of user object ");
		if (user.isPresent()) {
			logger.info("User Object returned successfully");
			User nuser = user.get();
			UserDto userdto = new UserDto();
			userdto.setUserEmail(nuser.getUserEmail());
			userdto.setName(nuser.getUsername());
			return userdto;
		}
		else {
				throw new UserNotFoundException("User not found with user email " + name);
		}
	}
//	
//	
//	public String deleteUser(Integer userId) throws UserNotFoundException {
//		// TODO Auto-generated method stub
//		logger.info("Finding the optional object using userId from the database");
//		Optional<User> user = userRepo.findUserByuserId(userId);
//		logger.info("checking for the presence of user object");
//		if (user.isPresent()) {
//			logger.info("User has been deleted");
//			userRepo.delete(user.get());
//		} else {
//			throw new UserNotFoundException("User not found with user id " + userId);
//		}
//		logger.info("Returning deleted user object");
//		User deletedUser = user.get();
//		UserDto nUserDto = new UserDto();
//		nUserDto.setId(deletedUser.getId());
//		nUserDto.setUserEmail(deletedUser.getUserEmail());
//		nUserDto.setName(deletedUser.getUserName());
//		//return nUserDto;
//		return "User Deleted SuccessFully: "+ nUserDto.getName();
//
//	}
//	
//	
//	
//	public void addFavoriteLocation(User user, Location location) {
//        user.getFavoriteLocations().add(location);
//        userRepository.save(user);
//    }
//
//    public void removeFavoriteLocation(User user, Location location) {
//        user.getFavoriteLocations().remove(location);
//        userRepository.save(user);
//    }

}
