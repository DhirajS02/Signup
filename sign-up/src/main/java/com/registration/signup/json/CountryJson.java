package com.registration.signup.json;

import java.util.List;

import lombok.Data;

@Data
public class CountryJson {
private List<Country> countries;
private String errorCode;
private String errorMessage;
}
