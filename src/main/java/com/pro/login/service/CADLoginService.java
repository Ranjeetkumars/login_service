/**
 * 
 */
package com.pro.login.service;

import java.util.List;

import com.pro.login.servicedto.CADLoginServiceDTO;

/**
 * @author VENKAT_PRO
 *
 */
public interface CADLoginService {
 public List<CADLoginServiceDTO> getLogin(String strReqId,CADLoginServiceDTO objCadLoginServiceDTO);
}
