package com.weatherapp.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.weatherapp.entities.City;
import com.weatherapp.entities.User;
import com.weatherapp.repositories.UserRepository;


@Service

public class CityServiceImpl{

	@Autowired

	private UserRepository userRepo;

	public ResponseEntity<List<String>> getRecentSearchesByUsername() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		User currentUser = (User) authentication.getPrincipal();
		System.out.println(currentUser);
		String currentUsername = currentUser.getUserName();
		System.out.println(currentUser.getUserName());

		User user = userRepo.findUserByuserName(currentUsername).get();
		System.out.println(user);

		if (user!=null) {

			User user1 = user;

			List<City> recentSearches = user1.getRecentSearches();

			int limit = Math.min(recentSearches.size(), 5);

			List<City> limitedSearches = recentSearches.subList(recentSearches.size() - limit, recentSearches.size());

			Collections.reverse(limitedSearches);

			List<String> uniqueNames = new ArrayList<>();

			for (City search : limitedSearches) {

				if (!uniqueNames.contains(search.getName())) {

					uniqueNames.add(search.getName());

				}

			}

			return ResponseEntity.ok(uniqueNames);

		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}

}
