/**
 * 
 */
package com.pro.login.persistencedto;

import lombok.Data;

/**
 * @author VENKAT_PRO
 *
 */
@Data
public class SiteDetailsPerstenceDTO {
	private String siteIp;
	private String sitePublicIp;
	private String siteName;
	private String tenantName;
	private String startDate;
	private String endDate;
	private String siteId;
	private String strLatitude;
	private String strLongitude;
	private String serviceProviderName;
	private String serviceProviderId;
	private String moduleid;
}
