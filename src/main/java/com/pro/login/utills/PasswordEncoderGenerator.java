/**
 * 
 */
package com.pro.login.utills;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author VENKAT_PRO
 *
 */
public class PasswordEncoderGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		int i = 0;
//		while (i < 10) {
//			String password = "123456";
//			
//			String hashedPassword = passwordEncoder.encode(password);
//
//			System.out.println(hashedPassword);
//			System.out.println(passwordEncoder.matches(password, hashedPassword));
//			i++;
//		}
		System.out.println(passwordEncoder.matches("123456", "$2a$10$iXyuHxENGGBnIXkVancUyewqP8Qxv8vxWpzMkygBDzRIlmcKWDJG2"));
	}

}
