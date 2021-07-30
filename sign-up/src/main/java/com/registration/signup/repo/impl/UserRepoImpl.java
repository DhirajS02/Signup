package com.registration.signup.repo.impl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.registration.signup.util.AppConstant;
import com.registration.signup.util.QueryUtility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.registration.signup.json.User;
import com.registration.signup.mapper.UserMapper;
import com.registration.signup.repo.UserRepo;

@Repository
public class UserRepoImpl implements UserRepo {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired 
	QueryUtility queryUtil;

	@Override
	public User registration(User user,LocalDate date) {
		String query = queryUtil.getProperty(AppConstant.QUERY_FOR_ADDUSER);
		Object[] params = new Object[] {user.getCustomerId(),user.getUserName(),user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(),
				user.getCountry(),user.getZip(),user.getState(),user.getCity(),user.getStreet(),date,user.getPhoneNumber()};
		int i=jdbcTemplate.update(query, params);
		if(i>0)
		{
			//return new ResponseMessage(user,HttpStatus.CREATED);
			return user;
		}
		return null;
//		else
//		{
//			System.out.println("incorrect");
//			return new ResponseMessage(null,HttpStatus.BAD_REQUEST);
//
//		}		
	}
	@Override
	public int isCustomerIdPresent(int customerId)
	{
		String query = queryUtil.getProperty(AppConstant.QUERY_FOR_CHECKUSER);
		Object[] params = new Object[] {customerId};
		return jdbcTemplate.query(query,new UserMapper(),params).size();
    }
	@Override
	public int isEmailPresent(String email) {
		String query = queryUtil.getProperty(AppConstant.QUERY_FOR_CHECKEMAIL);
		Object[] params = new Object[] {email};
		return jdbcTemplate.queryForObject(query,Integer.class,params);
	}
	@Override
	public int isUserNamePresent(String userName) {
		String query = queryUtil.getProperty(AppConstant.QUERY_FOR_CHECK_USERNAME);
		Object[] params = new Object[] {userName};
		return jdbcTemplate.queryForObject(query,Integer.class,params);
	}

}
