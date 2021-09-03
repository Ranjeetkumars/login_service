/**
 * 
 */
package com.pro.login.servicedto;

import java.util.List;

import com.pro.login.controllerdto.Modules;
import com.pro.login.controllerdto.Roles;

import lombok.Data;

/**
 * @author VENKAT_PRO
 *
 */
@Data
public class SupportLoginServiceDTO {
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
