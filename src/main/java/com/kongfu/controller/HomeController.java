package com.kongfu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kongfu.base.BaseController;

@Controller
@RequestMapping("/home")
public class HomeController extends BaseController {

	@RequestMapping(method = RequestMethod.GET, value = "/index")
	String index() {
		return "/index";
	}
}
