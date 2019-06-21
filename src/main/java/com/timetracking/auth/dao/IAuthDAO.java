package com.timetracking.auth.dao;

import com.timetracking.user.model.User;

public interface IAuthDAO 
{
	User getAccount(String emailStr, String passwordStr);
}
