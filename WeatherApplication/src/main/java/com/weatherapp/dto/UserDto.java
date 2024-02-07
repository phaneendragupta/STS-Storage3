package com.weatherapp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserDto {
	private int id;
	private String name;
	private String userEmail;


//	@Override
//	public String toString() {
//		return "UserDto [name=" + name + ", userEmail=" + userEmail + "]";
//	}

}
