/**
 * 
 */
package com.pro.login.servicedto;

import java.util.List;

import com.pro.login.controllerdto.Roles;

import lombok.Data;

/**
 * @author VENKAT_PRO
 *
 */
@Data
public class CADLoginServiceDTO {
	private String userID;
	private String username;
	private String password;
	private String moduleid;
	private String moduleName;
	private List<Roles> roles;
	private String tockenID;
	private String shiftId;
	private String shiftName;
	private String loginDateandTime;
	private String localIp;
	private String publicIp;
	private String loginLatitude;
	private String loginLongitude;
	private String siteID;
	private String errorCode;
	private String errorName;
	private String cti_userid;
	private String cti_username;
	private String cti_Extenstion;
	private String cti_Extenstion_password;
	private String name;
	private String imgepath;
	private String ceneterid;
}
