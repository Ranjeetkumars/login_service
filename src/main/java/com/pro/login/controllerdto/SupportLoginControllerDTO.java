/**
 * 
 */
package com.pro.login.controllerdto;

import java.util.List;

import lombok.Data;

/**
 * @author VENKAT_PRO
 *
 */
@Data
public class SupportLoginControllerDTO {
	private String userID;
	private String username;
	private String password;
	private List<Roles> roles;
	private List<Modules> modules;
	private String tockenID;
	private String loginDateandTime;
	private String localIp;
	private String publicIp;
	private String loginLatitude;
	private String loginLongitude;
	private String siteID;
	private String errorCode;
	private String errorName;
	private String name;
	private String imgepath;
	private String ceneterid;
}
