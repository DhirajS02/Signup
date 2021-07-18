package com.registration.signup.json;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
	@JsonIgnore
    private String customerId;
    @NotBlank(message = "UserName is mandatory")
    @NotNull(message = "UserName cannot be null")
	@Size(min=7, max=12,message="Username must be between 7 and 12 characters")
    private String userName;
    @NotBlank(message = "password is mandatory")
    @NotNull(message = "password cannot be null")
	@Size(min=8, max=14,message="Password must be between 8 and 14 characters")
	private String password;
    @NotBlank(message = "email is mandatory")
    @NotNull(message = "email cannot be null")
	private String email;
    @NotBlank(message = "firstname is mandatory")
    @NotNull(message = "firstname cannot be null")
	private String firstName;
    @NotBlank(message = "lastname is mandatory")
    @NotNull(message = "lastname cannot be null")
	private String lastName;
    @NotBlank(message = "country is mandatory")
    @NotNull(message = "country cannot be null")
	private String country;
    @NotBlank(message = "zip is mandatory")
    @NotNull(message = "zip cannot be null")
	private String zip;
    @NotBlank(message = "state is mandatory")
    @NotNull(message = "state cannot be null")
	private String state;
    @NotBlank(message = "city is mandatory")
    @NotNull(message = "city cannot be null")
	private String city;
    @NotBlank(message = "street is mandatory")
    @NotNull(message = "street cannot be null")
	private String street;
    @NotBlank(message = "dob is mandatory")
    @NotNull(message = "dob cannot be null")
	private String dob;
    @NotBlank(message = "phoneNumber is mandatory")
    @NotNull(message = "phoneNumber cannot be null")
	private String phoneNumber;
    
}
