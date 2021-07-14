package com.registration.signup.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    private String customerId;
    private String userName;
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
