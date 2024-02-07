package com.weatherapp.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
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
import com.weatherapp.weatherentities.Day;
import com.weatherapp.weatherentities.ForecastDay;
import com.weatherapp.weatherentities.WeatherForecast;


@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/forecast/")
public class ForecastApi {

	private String error;
    private boolean isValid;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CityRepository cityRepo;

    private static final Logger logger = LogManager.getLogger(ForecastApi.class);

    private WeatherForecast weatherForecast = new WeatherForecast();

	private String apiKey = "0c8ec075f30f4a9bba672344231110";

	@GetMapping("/name/{place}/{days}")
	public ResponseEntity<Object> responseName(@PathVariable("place") final String place,@PathVariable("days")  int days) {

		logger.info("Received request for place: {} and days: {}", place, days);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		User currentUser = (User) authentication.getPrincipal();
		System.out.println(currentUser);
		String currentUsername = currentUser.getUserName();
		System.out.println(currentUser.getUserName());

		User user = userRepo.findUserByuserName(currentUsername).get();
		System.out.println(user);

		if (user != null) {
			City city = new City();

			city.setName(place);

			city.setUser(user);

			city.setTimestamp(new Date());

			// Save the search information to the SearchRepository

			cityRepo.save(city);

		}

		String request = getResponse(
        		"http://api.weatherapi.com/v1/forecast.json?key="+apiKey+"&q="+place+"&days="+days+"&aqi=no&alerts=no");

		if (!(request.startsWith("Error"))) {

			logger.info("Received response from weather API");

			updateData(request);

			if (isValid) {
				logger.info("Returning valid weather forecast");
                return new ResponseEntity<>(weatherForecast,HttpStatus.OK);
            } else {
            	logger.info("Returning error response: {}", error);
                return new ResponseEntity<>(error,HttpStatus.OK);
            }

        } else {
        	logger.info("Error processing the request for place: {}", place);
            return new ResponseEntity<>("There was an error processing the request Invalid Place",HttpStatus.OK);
        }
    }

	private void updateData(String request) {
//		System.out.println("entered update data");
        isValid = false;
        error = "";
        JSONObject ob = getDataObject(request);
        System.out.println(isValid);
        if (isValid) {
            JSONObject location = (JSONObject) ob.get("location");
            JSONObject current = (JSONObject) ob.get("current");
            JSONObject condition = (JSONObject) current.get("condition");
            JSONObject forecast = (JSONObject) ob.get("forecast");
            JSONArray forecastday = (JSONArray) forecast.get("forecastday");

            weatherForecast.setCountry((String) location.get("country"));
            weatherForecast.setTemp_c((double) current.get("temp_c"));
            weatherForecast.setWeatherDescription((String) condition.get("text"));

            ArrayList<ForecastDay> forecastDayLoop = new ArrayList<>();
            for (Object element : forecastday) {
            	JSONObject temp = (JSONObject) element;
                ForecastDay data = new ForecastDay();

                data.setDate((String) temp.get("date"));

                	Day day = new Day();

                	JSONObject dayOb = (JSONObject) temp.get("day");
                	day.setMaxtemp_c((double) dayOb.get("maxtemp_c"));
                	day.setMintemp_c((double) dayOb.get("mintemp_c"));
                	day.setAvgtemp_c((double) dayOb.get("avgtemp_c"));
                	day.setMaxwind_kph((double) dayOb.get("maxwind_kph"));
                	day.setTotalprecip_mm((double) dayOb.get("totalprecip_mm"));
                	day.setAvghumidity((double) dayOb.get("avghumidity"));
                	day.setDaily_chance_of_rain((long) dayOb.get("daily_chance_of_rain"));
                	day.setUv((double) dayOb.get("uv"));

                    data.setDay(day);
                  forecastDayLoop.add(data);
            }
            weatherForecast.setForecastDayList(forecastDayLoop);
            logger.info("Weather forecast data updated successfully");
        }

    }

	private JSONObject getDataObject(String response){
//		System.out.println("entered data object");
		System.out.println(response);
    	JSONObject ob = null;
        try {
            ob = (JSONObject) new JSONParser().parse(response);
        } catch (ParseException e) {
            e.printStackTrace();
        }

		try {
			if (ob.containsKey("error")) {
				error = ob.get("error").toString();
				logger.info("Error in response: {}", error);
			} else {
				isValid = true;
				logger.info("Received valid data in response");
			}
		}
		catch (Exception e) {
			error = "Error in Fetching the Request";
			logger.info("Error processing response: {}", e.toString());
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
            logger.info("Received response from URL: {}", urlString);
            return content.toString();
        } catch (Exception e) {
//            System.out.println("Error: " + e.toString());
        	logger.info("Error while making an HTTP GET request: {}", e.toString());
            return "Error: " + e.toString();
        }
	}
}
