/**
 * 
 */
package com.pro.login.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.pro.login.controllerdto.CADLoginControllerDTO;
import com.pro.login.controllerdto.CADLogoutControllerDTO;
import com.pro.login.controllerdto.Response;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.exceptions.InSufficientInputException;
import com.pro.login.mappers.CADLoginMapper;
import com.pro.login.service.impl.CADLoginServiceImpl;
import com.pro.login.servicedto.CADLoginServiceDTO;
import com.pro.login.servicedto.CADLogoutServiceDTO;
import com.pro.login.wrappers.CADLoginWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VENKAT_PRO
 *
 */
@RestController
@RequestMapping("/cadlogin")
@Slf4j
public class CADLoginController {
	
	@Autowired
	private HttpServletRequest request;
	@Autowired
	Environment environment;
	@Autowired
	CADLoginServiceImpl objCADLoginServiceImpl;

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getCADLogin(@RequestBody CADLoginControllerDTO objCadLoginControllerDTO)
			throws InSufficientInputException, DataNotFoundException {
		String strRequestID = request.getAttribute("reqid").toString();
		String port = environment.getProperty("local.server.port");
//		String ip = InetAddress.getLocalHost().getHostAddress();
		CADLoginMapper  cadLoginMapper=new CADLoginMapper();
		CADLoginWrapper cadLoginWrapper=new CADLoginWrapper();
		CADLoginServiceDTO cadLoginServiceDTO=cadLoginMapper.convertCADLoginControllerDTOtoCADLoginServiceDTO(objCadLoginControllerDTO);
		//log.info(strRequestID + "::::getStates():::::INPUTS::ARE:::" + ip + port+"::::::::::::"+objCadLoginControllerDTO.toString());
		List<CADLoginServiceDTO> cadLoginServiceDTOs=objCADLoginServiceImpl.getLogin(strRequestID, cadLoginServiceDTO);
		cadLoginWrapper.setResponseCode(HttpStatus.OK.value());
//		cadLoginWrapper.setServerIp(ip);
//		cadLoginWrapper.setPort(port);
		cadLoginWrapper.setStatus(HttpStatus.OK.getReasonPhrase());
		cadLoginWrapper.setCadLoginControllerDTOs(cadLoginMapper.convertcadLoginServiceDTOstoobjCadLoginControllerDTOs(cadLoginServiceDTOs));		
		return cadLoginWrapper;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getCadLogout(@RequestBody CADLogoutControllerDTO objCadLoginControllerDTO)
			throws InSufficientInputException, DataNotFoundException {
		String strRequestID = request.getAttribute("reqid").toString();
		String port = environment.getProperty("local.server.port");
//		String ip = InetAddress.getLocalHost().getHostAddress();
		CADLoginMapper  cadLoginMapper=new CADLoginMapper();
		CADLoginWrapper cadLoginWrapper=new CADLoginWrapper();
		CADLogoutServiceDTO cadLoginServiceDTO=cadLoginMapper.convertCADLogoutControllerDTOtoCADLogoutServiceDTO(objCadLoginControllerDTO);
		//log.info(strRequestID + "::::getStates():::::INPUTS::ARE:::" + ip + port+"::::::::::::"+objCadLoginControllerDTO.toString());		
		String res=objCADLoginServiceImpl.getLogout(strRequestID, cadLoginServiceDTO);
		cadLoginWrapper.setResponseCode(HttpStatus.OK.value());
//		cadLoginWrapper.setServerIp(ip);
		cadLoginWrapper.setPort(port);
		cadLoginWrapper.setStatus(HttpStatus.OK.getReasonPhrase());
		cadLoginWrapper.setOutput(res);	
		return cadLoginWrapper;
	}
}
