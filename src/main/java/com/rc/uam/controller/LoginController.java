package com.rc.uam.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rc.uam.exception.UamException;
import com.rc.uam.model.User;
import com.rc.uam.service.UserService;
import com.rc.uam.utility.Constants;

/**
 * @author Rachit Bhasin
 *
 */

@Controller
public class LoginController {

	private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String handleUserLogin(ModelMap model, @RequestParam String email,
			@RequestParam String password) {
		User user = null;
		try {
			user = userService.getByField(Constants.EMAIL, email);
			if(user != null) {
				logger.info("============== Found user ============ " + user.getEmail() + " " + user.getPassword());
				logger.info("============== User attempt ============ " + email + " " + password);
				if(!user.getEmail().equalsIgnoreCase(email) && !user.getPassword().equals(password)) {
					model.put("errorMessage", "Invalid Credentials");
					return "login";
				}
			}
			
		} catch (UamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.put("name", user.getFirstName());
		return "welcome";
	}
}
