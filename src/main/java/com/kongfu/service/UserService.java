package com.kongfu.service;

import com.kongfu.entity.User;
import com.kongfu.vo.Result;

public interface UserService {

	public User findByUsername(String username);

	public User findByUsernameAndPassword(String username, String password);

	public Result validate(String author);

	public Result regist(User user);

	public Result getUserMessageById(String userId);
	
}
