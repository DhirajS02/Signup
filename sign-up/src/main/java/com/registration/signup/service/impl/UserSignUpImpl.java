package com.registration.signup.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.registration.signup.json.Country;
import com.registration.signup.json.CountryJson;
import com.registration.signup.json.FieldIntegrity;
import com.registration.signup.json.GeneralResponseJson;
import com.registration.signup.json.StatesJson;
import com.registration.signup.json.User;
import com.registration.signup.json.UserResponseJson;
import com.registration.signup.repo.UserRepo;
import com.registration.signup.service.UserSignUp;

@Service
public class UserSignUpImpl implements UserSignUp {

	@Autowired
	UserRepo userRepo;

	@Autowired
	RestTemplate restTemplate;

	@Override
	public UserResponseJson registration(User user) {
		UserResponseJson userJson = new UserResponseJson();
		userJson.setErrorCode("");
		userJson.setErrorMessage("");
		FieldIntegrity fieldIntegrity = new FieldIntegrity();

		String[] dobArray = user.getDob().split("/");
		LocalDate date = LocalDate.of(Integer.parseInt(dobArray[2]), Integer.parseInt(dobArray[1]),
				Integer.parseInt(dobArray[0]));
		user.setCustomerId(Integer.valueOf(createCustomerId()).toString());

		String errorMessage = new String();
		String errorField = new String();

		// Checking Email Validation & Existence
		fieldIntegrity = isEmailPresent(user.getEmail());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid email")) {
			errorMessage = errorMessage + "," + "invalid email";
			errorField = errorField + "," + "email";
			userJson.setErrorCode("email");

		}
		if (fieldIntegrity.getDescription().equalsIgnoreCase("email already present")) {
			errorMessage = errorMessage + "," + "email already present";
			errorField = errorField + "," + "email";
			userJson.setErrorCode("email");

		}

		// Checking Password Validation
		fieldIntegrity = passwordValidity(user.getPassword());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid password")) {
			errorMessage = errorMessage + "," + "invalid password";
			errorField = errorField + "," + "password";
			userJson.setErrorCode("password");

		}

		// Checking PhoneNumber Validation
		fieldIntegrity = phoneNumberValidity(user.getPhoneNumber());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid phoneNumber")) {
			errorMessage = errorMessage + "," + "invalid phoneNumber";
			errorField = errorField + "," + "phoneNumber";
			userJson.setErrorCode("phoneNumber");

		}

		// Checking FirstName Validation
		fieldIntegrity = firstNameValidity(user.getFirstName());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid firstName")) {
			errorMessage = errorMessage + "," + "invalid firstName";
			errorField = errorField + "," + "firstName";
			userJson.setErrorCode("firstName");

		}

		// Checking LastName Validation
		fieldIntegrity = lastNameValidity(user.getLastName());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid lastName")) {
			errorMessage = errorMessage + "," + "invalid lastName";
			errorField = errorField + "," + "lastName";
			userJson.setErrorCode("lastName");

		}

		// Checking Username Existence & Validation
		fieldIntegrity = userNameValidity(user.getUserName());
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid userName")) {
			errorMessage = errorMessage + "," + "invalid userName";
			errorField = errorField + "," + "userName";
			userJson.setErrorCode("userName");
		}
		if (fieldIntegrity.getDescription().equalsIgnoreCase("userName already present")) {
			errorMessage = errorMessage + "," + "userName already present";
			errorField = errorField + "," + "userName";
			userJson.setErrorCode("userName");

		}

		// Returning UserResponseJson after validation checks of data
		if (!userJson.getErrorCode().isEmpty()) {
			userJson.setUser(null);
			String errorMessages = errorMessage.substring(1, errorMessage.length());
			String errorFields = errorField.substring(1, errorField.length());
			userJson.setErrorCode(errorFields);
			userJson.setErrorMessage(errorMessages);

		} else {
			User addedUser = userRepo.registration(user, date);
			userJson.setUser(addedUser);
			userJson.setErrorCode("");
			userJson.setErrorMessage("");

		}
		return userJson;

	}

	private int createCustomerId() {
		int customerId = 0;
		do {
			customerId = getRandomNumber(9);
		} while (userRepo.isCustomerIdPresent(customerId) != 0);
		return customerId;
	}

	private int getRandomNumber(int lengthOfRandomNumbers) {
		Random randomNumberGenerator = new Random();
		String bound = "9";
		String additionalNumber = "1";
		for (int i = 0; i < lengthOfRandomNumbers - 1; i++) {
			bound = bound + "0";
			additionalNumber = additionalNumber + "0";

		}
		int randomNumbers = randomNumberGenerator.nextInt(Integer.parseInt(bound)) + Integer.parseInt(additionalNumber);
		return randomNumbers;

	}

	@Override
	public FieldIntegrity isEmailPresent(String email) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			fieldIntegrity.setField("email");
			fieldIntegrity.setDescription("invalid email");
			return fieldIntegrity;
		}

		if (userRepo.isEmailPresent(email) != 0) {
			fieldIntegrity.setField("email");
			fieldIntegrity.setDescription("email already present");

		} else {
			fieldIntegrity.setField("email");
			fieldIntegrity.setDescription("email already available");

		}
		return fieldIntegrity;
	}

	@Override
	public FieldIntegrity passwordValidity(String password) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String password_validator = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
		Pattern pattern = Pattern.compile(password_validator);
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			fieldIntegrity.setField("password");
			fieldIntegrity.setDescription("invalid password");
			return fieldIntegrity;
		}
		return fieldIntegrity;
	}

	@Override
	public StatesJson getStates(String countryId) {
		Map<String, String[]> countryStates = new HashMap<String, String[]>();
		String indStatesArray[] = { "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa",
				"Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala",
				"Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
				"Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh",
				"West Bengal", "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli", "Daman and Diu",
				"Delhi", "Lakshadweep", "Puducherry" };

		countryStates.put("IND", indStatesArray);

		List<String> states = Arrays.asList(countryStates.get(countryId));
		StatesJson stateJson = new StatesJson();
		stateJson.setStates(states);
		stateJson.setErrorCode("");
		stateJson.setErrorMessage("");

		return stateJson;
	}

	@Override
	public FieldIntegrity phoneNumberValidity(String phoneNumber) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String phoneNumber_validator = "(0|91)?[6-9][0-9]{9}";
		Pattern pattern = Pattern.compile(phoneNumber_validator);
		Matcher matcher = pattern.matcher(phoneNumber);
		if (!matcher.matches()) {
			fieldIntegrity.setField("phoneNumber");
			fieldIntegrity.setDescription("invalid phoneNumber");
			return fieldIntegrity;
		}
		return fieldIntegrity;
	}

	@Override
	public FieldIntegrity userNameValidity(String userName) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String regex = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(userName);
		if (!matcher.matches()) {
			fieldIntegrity.setField("userName");
			fieldIntegrity.setDescription("invalid userName");
			return fieldIntegrity;
		}
		if (userRepo.isUserNamePresent(userName) != 0) {
			fieldIntegrity.setField("userName");
			fieldIntegrity.setDescription("userName already present");

		} else {
			fieldIntegrity.setField("userName");
			fieldIntegrity.setDescription("userName already available");

		}
		return fieldIntegrity;
	}

	@Override
	public FieldIntegrity firstNameValidity(String firstName) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String regex = "^[a-zA-Z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(firstName);
		if (!matcher.matches()) {
			fieldIntegrity.setField("firstName");
			fieldIntegrity.setDescription("invalid firstName");
			return fieldIntegrity;
		}
		return fieldIntegrity;
	}

	@Override
	public FieldIntegrity lastNameValidity(String lastName) {
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity.setField("");
		fieldIntegrity.setDescription("");
		String regex = "^[a-zA-Z]*$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(lastName);
		if (!matcher.matches()) {
			fieldIntegrity.setField("lastName");
			fieldIntegrity.setDescription("invalid lastName");
			return fieldIntegrity;
		}
		return fieldIntegrity;
	}

	@Override
	public List<String> suggestUserNames(String userName) {
		List<String> userNamesList = new ArrayList<String>();
		FieldIntegrity fieldIntegrity = new FieldIntegrity();
		fieldIntegrity = userNameValidity(userName);
		Random randomNumberGenerator = new Random();
		String suggestedUserNameWithDot = "";
		String suggestedUserNameWithUnderScore = "";
		String suggestedUserNameWithShuffledcharacter1 = "";
		String suggestedUserNameWithShuffledcharacter2 = "";
		if (fieldIntegrity.getDescription().equalsIgnoreCase("invalid userName")) {
			userNamesList.add("invalid userName");
			return userNamesList;
		}
		if (fieldIntegrity.getDescription().equalsIgnoreCase("userName already available")) {
			userNamesList.add("userName available");
			return userNamesList;

		}

		int n = userName.length();

		if (n == 20) {
			suggestedUserNameWithDot = userName.substring(0, n / 2) + "." + userName.substring((n / 2), n - 1);
			while (userRepo.isUserNamePresent(suggestedUserNameWithDot) != 0) {
				int increment = 1;
				suggestedUserNameWithDot = userName.substring(0, (n / 2) + increment) + "."
						+ userName.substring((n / 2) + increment, n - 1);
				increment++;
			}
		} else {
			suggestedUserNameWithDot = userName.substring(0, n / 2) + "." + userName.substring((n / 2), n);
			while (userRepo.isUserNamePresent(suggestedUserNameWithDot) != 0) {
				int increment = 1;
				suggestedUserNameWithDot = userName.substring(0, (n / 2) + increment) + "."
						+ userName.substring((n / 2) + increment, n);
				increment++;
			}
		}
		userNamesList.add(suggestedUserNameWithDot);

		if (n == 20) {
			suggestedUserNameWithUnderScore = userName.substring(0, n / 2) + "_" + userName.substring((n / 2), n - 1);
			while (userRepo.isUserNamePresent(suggestedUserNameWithUnderScore) != 0) {
				int increment = 1;
				suggestedUserNameWithUnderScore = userName.substring(0, (n / 2) + increment) + "_"
						+ userName.substring((n / 2) + increment, n - 1);
				increment++;
			}
		} else {
			suggestedUserNameWithUnderScore = userName.substring(0, n / 2) + "_" + userName.substring((n / 2), n);
			while (userRepo.isUserNamePresent(suggestedUserNameWithUnderScore) != 0) {
				int increment = 1;
				suggestedUserNameWithUnderScore = userName.substring(0, (n / 2) + increment) + "_"
						+ userName.substring((n / 2) + increment, n);
				increment++;
			}
		}
		userNamesList.add(suggestedUserNameWithUnderScore);

		if (n == 20) {
			suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 2) + randomNumberGenerator.nextInt(99);
			while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter1) != 0) {
				suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 2)
						+ randomNumberGenerator.nextInt(99);
			}
		} else {
			if (n == 19) {
				suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 2)
						+ randomNumberGenerator.nextInt(999);
				while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter1) != 0) {
					suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 2)
							+ randomNumberGenerator.nextInt(999);
				}
			} else if (n == 18) {
				suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 1)
						+ randomNumberGenerator.nextInt(999);
				while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter1) != 0) {
					suggestedUserNameWithShuffledcharacter1 = userName.substring(0, n - 1)
							+ randomNumberGenerator.nextInt(999);
				}
			} else {
				suggestedUserNameWithShuffledcharacter1 = userName
						+ randomNumberGenerator.nextInt(999);
			}
		}
		userNamesList.add(suggestedUserNameWithShuffledcharacter1);

		if (n == 20) {
			suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(99) + userName.substring(0, n - 2);
			while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter2) != 0) {
				suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(99)
						+ userName.substring(0, n - 2);
			}
		} else {

			if (n == 19) {
				suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(999)
						+ userName.substring(0, n - 2);

				while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter2) != 0) {
					suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(999)
							+ userName.substring(0, n - 2);

				}
			} else if (n == 18) {
				suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(999)
						+ userName.substring(0, n - 1);
				while (userRepo.isUserNamePresent(suggestedUserNameWithShuffledcharacter2) != 0) {
					suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(999)
							+ userName.substring(0, n - 1);

				}
			} else {
				suggestedUserNameWithShuffledcharacter2 = randomNumberGenerator.nextInt(999)
						+ userName;
			}
		}
		userNamesList.add(suggestedUserNameWithShuffledcharacter2);

		return userNamesList;

	}

}
