/**
 * 
 */
package com.pro.login.service;

import java.util.List;

import com.pro.login.servicedto.SupportLoginServiceDTO;

/**
 * @author VENKAT_PRO
 *
 */
public interface SupportLoginService {
	 public List<SupportLoginServiceDTO> getLogin(String strReqId,SupportLoginServiceDTO objSupportLoginControllerDTO);
}
