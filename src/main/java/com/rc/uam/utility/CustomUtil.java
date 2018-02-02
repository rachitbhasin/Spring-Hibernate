package com.rc.uam.utility;

import com.rc.uam.model.User;

/**
 * @author Rachit Bhasin
 *
 */

public final class CustomUtil {
	/**
	 * function to get logged in user details
	 *
	 * @return
	 */
	public static User getLoggedInUser() {
		User user = new User();
		
		user.setFirstName("Admin");
		user.setLastName("lastname");
		user.setEmail("admin@admin.com");
		user.setId(1L);
		user.setPassword("1234556");
		
		return user;
	}
}
