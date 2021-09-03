/**
 * 
 */
package com.pro.login.service;

import java.util.List;

import com.pro.login.servicedto.SiteDetailsServiceTO;

/**
 * @author VENKAT_PRO
 *
 */
public interface SiteValidatorService {
 public List<SiteDetailsServiceTO> getSteDetails(String strReqid,SiteDetailsServiceTO objServiceTO);
 public List<SiteDetailsServiceTO> getSteModuleDetails(String strReqid,SiteDetailsServiceTO objServiceTO);
}
