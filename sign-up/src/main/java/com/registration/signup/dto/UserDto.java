package com.registration.signup.dto;

import lombok.Data;

@Data
public class UserDto {
	 private String customerId;
		private String password;
		private String email;
		private String firstName;
		private String lastName;
		private String country;
		private String zip;
		private String state;
		private String city;
		private String street;
		private String dob;
		private String phoneNumber;
}
