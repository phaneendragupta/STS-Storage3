package com.weatherapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.weatherapp.entities.City;
import com.weatherapp.entities.User;
import com.weatherapp.repositories.CityRepository;
import com.weatherapp.repositories.UserRepository;

@Service
public class FavouriteServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CityRepository cityRepository;



	public ResponseEntity<String> markAsFavorite(String name) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		User currentUser = (User) authentication.getPrincipal();
		System.out.println(currentUser);
		String currentUsername = currentUser.getUserName();
		System.out.println(currentUser.getUserName());

		User user = userRepository.findUserByuserName(currentUsername).get();
		System.out.println(user);



	        if (user!=null) {

	            User currUser = user;



	            // Find all searches with the same name for the user

	            List<City> searchesWithSameName = currUser.getRecentSearches()

	                .stream()

	                .filter(s -> s.getName().equals(name))

	                .collect(Collectors.toList());



	            if (searchesWithSameName.isEmpty()) {

	                return ResponseEntity.notFound().build();

	            }


	            searchesWithSameName.forEach(search -> search.setFavorite(true));

	            cityRepository.saveAll(searchesWithSameName);



	            return ResponseEntity.ok("City " + name + " marked as favorite.");

	        }



	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");

	    }











	public ResponseEntity<List<String>> getFavoriteSearches() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		User currentUser = (User) authentication.getPrincipal();
		System.out.println(currentUser);
		String currentUsername = currentUser.getUserName();
		System.out.println(currentUser.getUserName());

		User user = userRepository.findUserByuserName(currentUsername).get();


	    if (user!=null) {

	        User currUser = user;

	        List<String> favoriteSearchNames = currUser.getRecentSearches()

	            .stream()

	            .filter(City::isFavorite) // Filter by favorite searches

	            .map(City::getName) // Get the names of favorite searches

	            .distinct()

	            .collect(Collectors.toList());



	        return ResponseEntity.ok(favoriteSearchNames);

	    }



	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}





}
