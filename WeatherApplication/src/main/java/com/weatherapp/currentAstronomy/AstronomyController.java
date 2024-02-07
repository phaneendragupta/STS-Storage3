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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weatherapp.entities.City;
import com.weatherapp.entities.User;
import com.weatherapp.repositories.CityRepository;
import com.weatherapp.repositories.UserRepository;


@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/api/astronomy")
public class AstronomyController {

	String error;

    boolean isValid;

    private String location;

    private String sunrise;

    private String sunset;

    private String moonrise;

    private String moonset;

    private String moon_phase;

    private String moon_illumination;

    private String apiKey = "0c8ec075f30f4a9bba672344231110";

    @Autowired
	private UserRepository userRepo;

	@Autowired
	private CityRepository cityRepo;

	
    //@SuppressWarnings("unchecked")
	@RequestMapping("/{place}")

    public ResponseEntity<String> responseName(@PathVariable("place") final String place) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = (User) authentication.getPrincipal();
		String currentUsername = currentUser.getUserName();

		User user = userRepo.findUserByuserName(currentUsername).get();


    	if (user != null) {
    		City city = new City();

    		city.setName(place);

    		city.setUser(user);

    		city.setTimestamp(new Date());

    		cityRepo.save(city);

    	}

        String request = getResponse(

                "http://api.weatherapi.com/v1/astronomy.json?key="+apiKey+"&q="+place+"&dt=2023-10-05");

        if (!(request.startsWith("Error"))) {

            updateData(request);

            if (isValid) {
            	JSONObject jsonObject = new JSONObject();

		    	jsonObject.put("sunrise", sunrise);

		    	jsonObject.put("sunset", sunset);

		    	jsonObject.put("moonrise", moonrise);

		    	jsonObject.put("moon_phase", moon_phase);

		    	jsonObject.put("moon_illumination", moon_illumination);

//                return "Astronomical report of : " + place + "<br>Munrise:" + sunrise + "<br>Sunset: "
//
//                        + sunset + "<br>Moonrise: " + moonrise + "<br>Moonset: " + moonset + "<br>Moon_Phase: "
//
//                        + moon_phase + "<br>Moon_Illumination: " + moon_illumination ;
		    	return ResponseEntity.ok(jsonObject.toString());
            } else {

            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }

        } else {

        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There was an error processing the request\nInvalid Place");

        }

    }

//        @RequestMapping("astronomy/pin/{pinCode}")
//
//        public ResponseEntity<String>  responsePin(@PathVariable("pinCode") final String pincode) {
//
//            String request = getResponse(
//
//                    "http://api.openweathermap.org/data/2.5/weather?zip=" + pincode + ",in&units=metric&appid=" + apiKey);
//
//            if (!(request.startsWith("Error"))) {
//
//                updateData(request);
//
//                if (isValid) {
//
//                	JSONObject jsonObject = new JSONObject();
//
//    		    	jsonObject.put("sunrise", sunrise);
//
//    		    	jsonObject.put("sunset", sunset);
//
//    		    	jsonObject.put("moonrise", moonset);
//
//    		    	jsonObject.put("moon_phase", moon_phase);
//
//    		    	jsonObject.put("moon_illumination", moon_illumination);
//
//
//
//
//
//
//
//
//
//    		    return ResponseEntity.ok(jsonObject.toString());
//
////                	return "Astronomical report of : " + location + "<br>Sunrise:" + sunrise + "<br>Sunset: "
//
////                            + sunset + "<br>Moonrise: " + moonrise + "<br>Moonset: " + moonset + "<br>Moon_Phase: "
//
////                            + moon_phase + "<br>Moon_Illumination: " + moon_illumination ;
//
//                } else {
//
//                	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//
//                }
//
//            } else {
//
//            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There was an error processing the request\nInvalid Place");
//
//            }
//
//        }
//


       // The updateData method  updates someclass-level variables

        // based on the data obtained from a JSON response.

        private void updateData(String request) {

            isValid = false;

            error = "";

            JSONObject ob = getDataObject(request);

            System.out.println(isValid);

            if (isValid) {

                JSONObject astronomy = (JSONObject) ob.get("astronomy");

                JSONObject astro = (JSONObject) astronomy.get("astro");

                sunrise=(String)astro.get("sunrise");

                sunset=(String)astro.get("sunset");

               moonrise=(String)astro.get("moonrise");

             moonset=(String)astro.get("moonset");

            moon_phase=(String)astro.get("moon_phase");

            moon_illumination=(String) astro.get("moon_illumination");



            }

        }

//The getDataObject is a Java method that takes a JSON-formatted response as input and

        //attempts to parse it into a JSONObject.-It also handles error cases

        // by checking for the presence of an "error" key in the JSON response.

        private JSONObject getDataObject(String response) {

            JSONObject ob = null;

            try {

                ob = (JSONObject) new JSONParser().parse(response);

            } catch (ParseException e) {

                e.printStackTrace();

            }

            try {

               if(ob.containsKey("error"))

               {

            	   error=ob.get("error").toString();

               }

               else

               {

            	   isValid=true;

               }

            } catch (Exception e) {

                error = "Error in Fetching the Request";

            }

            return ob;

        }



       // The getResponse  is a Java method for making an HTTP GET request to a

        //specified URL and returning the response as a string. This method uses

        //Java's HttpURLConnection to establish a connection and retrieve the response data



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