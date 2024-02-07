package com.weatherapp.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.weatherapp.entities.User;

@Component
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
        user.setId(rs.getInt("id"));
        user.setUserName(rs.getString("user_name"));
        user.setUserEmail(rs.getString("user_email"));
        user.setPassword(rs.getString("password"));
        return user;
	}

}
