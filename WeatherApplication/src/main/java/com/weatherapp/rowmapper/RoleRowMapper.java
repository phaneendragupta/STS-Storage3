package com.weatherapp.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.weatherapp.entities.Role;

public class RoleRowMapper implements RowMapper<Role> {

	@Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setRoleId(rs.getInt("id"));
        role.setRole_name(rs.getString("role_name"));
        return role;
    }

}
