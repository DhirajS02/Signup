package com.registration.signup.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:query.properties")
public class QueryUtility {
	@Autowired
	private Environment env;
	
	public String getProperty(String key) 
	{
		return env.getProperty(key);
	}
}
