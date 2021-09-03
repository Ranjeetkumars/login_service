package com.pro.login.persistencedto;

import lombok.Data;

@Data
public class UserShiftsPersistenceDTO {
	
	private String moduleId;
	private String shiftId;
	private String shiftType;
	private String siteId;
	private String serviceproviderId;
	private String Ip;
}
