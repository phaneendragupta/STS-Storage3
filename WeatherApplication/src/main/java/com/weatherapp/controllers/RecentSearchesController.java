package com.weatherapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.services.CityServiceImpl;


@RestController
@RequestMapping("/city/")
public class RecentSearchesController {

	@Autowired

	private CityServiceImpl cityService;

	@GetMapping("/recent")

	public ResponseEntity<List<String>> getRecentSearchesForCurrentUser() {

		return cityService.getRecentSearchesByUsername();

}

}
