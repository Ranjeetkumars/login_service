/**
 * 
 */
package com.pro.login.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.SupportLoginControllerDTO;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.exceptions.InSufficientInputException;
import com.pro.login.mappers.SupportLoginMapper;
import com.pro.login.service.SupportLoginService;
import com.pro.login.servicedto.SupportLoginServiceDTO;
import com.pro.login.wrappers.SupportLoginWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VENKAT_PRO
 *
 */
@RestController
@RequestMapping("/supportlogin")
@Slf4j
public class SupportLoginController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	Environment environment;
	@Autowired
	@Qualifier("objSupportLoginServiceImpl")
	SupportLoginService objSupportLoginServiceImpl;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getSupportAppsLogin(@RequestBody SupportLoginControllerDTO objSupportLoginControllerDTO)
			throws  InSufficientInputException, DataNotFoundException {
		String strRequestID = request.getAttribute("reqid").toString();
		String port = environment.getProperty("local.server.port");
//		String ip = InetAddress.getLocalHost().getHostAddress();
		SupportLoginMapper  loginMapper=new SupportLoginMapper();
		SupportLoginWrapper loginWrapper=new SupportLoginWrapper();
		
		SupportLoginServiceDTO loginServiceDTO=loginMapper.convertCADLoginControllerDTOtoCADLoginServiceDTO(objSupportLoginControllerDTO);
//		log.info(strRequestID + "::::getStates():::::INPUTS::ARE:::" + ip + port+"::::::::::::"+objSupportLoginControllerDTO.toString());
		List<SupportLoginServiceDTO> cadLoginServiceDTOs=objSupportLoginServiceImpl.getLogin(strRequestID, loginServiceDTO);
		loginWrapper.setResponseCode(HttpStatus.OK.value());
//		loginWrapper.setServerIp(ip);
		loginWrapper.setPort(port);
		loginWrapper.setStatus(HttpStatus.OK.getReasonPhrase());
		loginWrapper.setObjSupportLoginControllerDTOs(loginMapper.convertSupportLoginServiceDTOstoobjSupportLoginControllerDTOs(cadLoginServiceDTOs));		
		return loginWrapper;
	}
}
