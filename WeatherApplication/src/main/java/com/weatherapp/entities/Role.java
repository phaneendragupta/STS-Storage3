package com.weatherapp.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Data
@Getter
@Setter
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer roleId;

	@Column
	private String role_name;

	//mappedBy to establish a bidirectional relationship
	@ManyToMany(mappedBy = "roles")
	private Set<User> weatherUsers;


}
