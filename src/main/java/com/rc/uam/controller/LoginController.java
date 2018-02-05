package com.rc.uam.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rc.uam.service.UserService;
import com.rc.uam.utility.CustomUtil;

/**
 * @author Rachit Bhasin
 *
 */

@Controller
public class LoginController {

	//private static final Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String showLoginPage() {
		if(CustomUtil.isUserLoggedIn()) {
			return "redirect:/";
		}
		
		return "login";
	}
	
	@RequestMapping(value="/Access_Denied", method = RequestMethod.GET)
	public String showAccessDeniedPage() {
		return "accessdenied";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
        	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        	logoutHandler.setInvalidateHttpSession(true);
        	logoutHandler.logout(request, response, auth);            
        }
        return "redirect:/login?logout";
    }
}
