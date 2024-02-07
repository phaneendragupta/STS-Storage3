package com.weatherapp.currentAstronomy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.entities.City;
import com.weatherapp.entities.User;
import com.weatherapp.repositories.CityRepository;
import com.weatherapp.repositories.UserRepository;
import com.weatherapp.security.JwtTokenUtil;

@RestController
@RequestMapping("/api/weather/")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class weatherController {

	String error;
	boolean isValid;
	private String last_updated;
	private double temp_c;
	private double temp_f;
	private double feelslike_c;
	private double feelslike_f;
	private double wind_kph;
	private int humidity;
	private int cloud;

	private String apiKey = "0c8ec075f30f4a9bba672344231110";

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CityRepository cityRepo;

	@GetMapping("{location}")
	public ResponseEntity<String> getByLocation(@PathVariable("location") String location) throws ParseException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		String currentUsername = currentUser.getUserName();

		User user = userRepo.findUserByuserName(currentUsername).get();
		
		if (user != null) {
			City city = new City();

			city.setName(location);

			city.setUser(user);

			city.setTimestamp(new Date());

			// Save the search information to the SearchRepository

			cityRepo.save(city);

		}
		String request = getResponse(
				"https://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + location + "&aqi=no");
		if (!(request.startsWith("Error"))) {
			System.out.println("entered first if");
			updateData(request);

			if (isValid) {

				System.out.println("entered second if");
				System.out.println(last_updated);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("last_updated", last_updated);
				jsonObject.put("temp_c", temp_c);
				jsonObject.put("temp_f", temp_f);
				jsonObject.put("feelslike_c", feelslike_c);
				jsonObject.put("feelslike_f", feelslike_f);
				jsonObject.put("wind_kph", wind_kph);
				jsonObject.put("humidity", humidity);
				jsonObject.put("cloud", cloud);

				return ResponseEntity.ok(jsonObject.toString());

			} else {
				System.out.println("inner if error");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
			}

		} else {
			// .System.out.println("outer if error");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("There was an error processing the request\nInvalid Place");
		}

	}

	private void updateData(String request) throws ParseException {

		isValid = false;
		error = "";
		JSONObject ob = getDataObject(request);
		System.out.println("000000000" + request);
		System.out.println(isValid);
		System.out.println("entered update data");
		if (isValid) {

			JSONObject responseJson = (JSONObject) new JSONParser().parse(request);
			JSONObject currentWeather = (JSONObject) responseJson.get("current");

			last_updated = (String) currentWeather.get("last_updated");
			temp_c = (double) currentWeather.get("temp_c");
			temp_f = (double) currentWeather.get("temp_f");
			feelslike_c = (double) currentWeather.get("feelslike_c");
			feelslike_f = (double) currentWeather.get("feelslike_f");
			wind_kph = (double) currentWeather.get("wind_kph");
			humidity = ((Long) currentWeather.get("humidity")).intValue();
			cloud = ((Long) currentWeather.get("cloud")).intValue();

		}
	}

	private JSONObject getDataObject(String response) {
		JSONObject ob = null;
		System.out.println(" ==========" + response);
		try {
			ob = (JSONObject) new JSONParser().parse(response);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			if (ob.containsKey("error")) {
				error = ob.get("error").toString();
			} else {
				isValid = true;
			}

		} catch (Exception e) {
			error = "Error in Fetching the Request";
		}
		return ob;

	}

	private String getResponse(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();
			return content.toString();

		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
			return "Error: " + e.toString();
		}

	}

}