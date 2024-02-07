//package com.weatherapp.weatherentities;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import com.weatherapp.entities.Role;
//import com.weatherapp.entities.User;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.Table;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Entity
//@Table
//@Data
//@Getter
//@Setter
//public class Location {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String city;
//
//    // Define the many-to-many relationship with users
//    @ManyToMany(mappedBy = "favoriteLocations")
//    private Set<User> users = new HashSet<>();
//
//
//}
