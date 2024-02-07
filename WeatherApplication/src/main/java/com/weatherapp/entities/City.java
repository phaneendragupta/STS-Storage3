package com.weatherapp.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class City {
	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)

	private int id;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)

	private Date timestamp;

	private boolean favorite;

	@ManyToOne

	@JoinColumn(name = "user_id")

	@JsonIgnore

	private User user;



}
