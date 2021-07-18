package com.registration.signup.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.jdbc.core.RowMapper;

import com.registration.signup.json.User;

public class UserMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		
		return new User(				
				Integer.valueOf(rs.getInt("customerid")).toString() == null ? "" : Integer.valueOf(rs.getInt("customerid")).toString(),								
                    			(rs.getString("username") == null) ? "" : rs.getString("username").trim(),
                    			(rs.getString("email") == null) ? "" : rs.getString("email").trim(),
                    			(rs.getString("password") == null) ? "" : rs.getString("password").trim(),
				                (rs.getString("first_name") == null) ? "" : rs.getString("firstName").trim(),
				                (rs.getString("last_name") == null) ? "" : rs.getString("lastName").trim(),
				                (rs.getString("country") == null) ? null : rs.getString("country"),
				                (rs.getString("zip") == null) ? "" : rs.getString("zip").trim(),
				                (rs.getString("state") == null) ? "" : rs.getString("state").trim(),
				                (rs.getString("city") == null) ? "" : rs.getString("city").trim(),
				                (rs.getString("street") == null) ? "" : rs.getString("street").trim(),
                                (rs.getDate("dob") == null) ? null : rs.getDate("dob").toString(),
                                (rs.getString("phonenumber") == null) ? "" : rs.getString("phonenumber").trim()
                );	
		
	}

}
