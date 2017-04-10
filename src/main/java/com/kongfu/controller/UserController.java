package com.kongfu.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongfu.base.BaseController;
import com.kongfu.entity.User;
import com.kongfu.service.UserService;
import com.kongfu.vo.Result;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 跳到登录页
	 * SunJB 2017年1月14日 下午4:28:04
	 * @param request
	 * @return
	 */
	@RequestMapping("/toLogin")
	String toLogin(HttpServletRequest request) {
//		// 获取请求头身份信息
//		String author = request.getHeader("Authorization");
//		Result result = userService.validate(author);
		return "login";
	}
	/**
	 * 登录
	 * SunJB 2017年1月14日 下午4:28:04
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST,value="/login")
	@ResponseBody
	String login(String username,String password,HttpServletRequest request) {

		return "login";
	}
	
	@RequestMapping(method = RequestMethod.POST,value="/regist")
	@ResponseBody
	public Result regist(User user){
		return userService.regist(user);
	}

	@RequestMapping("/index")
	@ResponseBody
	public Result indexMessage(String userId){
		return userService.getUserMessageById(userId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/hello/{name}")
	public String hello(@PathVariable("name") String name, Model model) {
		User user = userService.findByUsername(name);
		model.addAttribute("user", user);
//		User user = new User();
//		user.setUname("admin");
//		userService.regist(user);
		return "hello";
	}
}
