package com.weatherapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.services.FavouriteServiceImpl;

@RestController
@RequestMapping("/api/weather/searches")

@CrossOrigin(origins = "*", allowedHeaders = "*")

public class FavouriteController {

	@Autowired

	private FavouriteServiceImpl fav;

	@PostMapping("/fav/{name}")

	public ResponseEntity<String> markAsFavorite(@PathVariable("name") String name) {

		return fav.markAsFavorite(name);

	}

	@GetMapping("/fav")

	public ResponseEntity<List<String>> getFavoriteSearches() {

		return fav.getFavoriteSearches();

	}

}
