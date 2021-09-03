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

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.SiteDetailsControllerDTO;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.exceptions.InSufficientInputException;
import com.pro.login.mappers.SiteDetailsMapper;
import com.pro.login.service.SiteValidatorService;
import com.pro.login.servicedto.SiteDetailsServiceTO;
import com.pro.login.wrappers.SiteDetailsWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author VENKAT_PRO
 *
 */
@RestController
@RequestMapping("/sitevalidator")
@Slf4j
public class SiteValidatorController {
	@Autowired
	private HttpServletRequest request;
	@Autowired
	Environment environment;
	
	@Autowired
	SiteValidatorService objSiteValidatorServiceImpl;

	@RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getSiteValidate(@RequestBody SiteDetailsControllerDTO objControllerDTO) throws  InSufficientInputException, DataNotFoundException {
		String strRequestID = request.getAttribute("reqid").toString();
		String port = environment.getProperty("local.server.port");
//		String ip = InetAddress.getLocalHost().getHostAddress();		
		SiteDetailsMapper objMapper=new SiteDetailsMapper();
		SiteDetailsServiceTO objServiceTO =objMapper.convertSiteDetailsControllerDTOtoSiteDetailsServiceDTO(objControllerDTO);
		objServiceTO.setSiteIp(request.getRemoteHost());
		List<SiteDetailsServiceTO> serviceTOs=objSiteValidatorServiceImpl.getSteDetails(strRequestID, objServiceTO);		
//		log.info(strRequestID + "::::getStates():::::INPUTS::ARE:::" + ip + port);
		SiteDetailsWrapper wrapper=new SiteDetailsWrapper();
//		wrapper.setServerIp(ip);
		wrapper.setPort(port);
		wrapper.setStatus(HttpStatus.OK.getReasonPhrase());
		wrapper.setResponseCode(HttpStatus.OK.value());
		wrapper.setControllerDTOs(objMapper.convertserviceTOstoobjControllerDTOs(serviceTOs));
		return wrapper;
	}
	
	@RequestMapping(value = "/validatemodule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getValidateSiteAndModule(@RequestBody SiteDetailsControllerDTO objControllerDTO) throws  InSufficientInputException, DataNotFoundException {
		String strRequestID = request.getAttribute("reqid").toString();
		String port = environment.getProperty("local.server.port");
//		String ip = InetAddress.getLocalHost().getHostAddress();		
		SiteDetailsMapper objMapper=new SiteDetailsMapper();
		SiteDetailsServiceTO objServiceTO =objMapper.convertSiteDetailsControllerDTOtoSiteDetailsServiceDTO(objControllerDTO);
		objServiceTO.setSiteIp(request.getRemoteHost());
		List<SiteDetailsServiceTO> serviceTOs=objSiteValidatorServiceImpl.getSteModuleDetails(strRequestID, objServiceTO);		
//		log.info(strRequestID + "::::getStates():::::INPUTS::ARE:::" + ip + port);
		SiteDetailsWrapper wrapper=new SiteDetailsWrapper();
//		wrapper.setServerIp(ip);
		wrapper.setPort(port);
		wrapper.setStatus(HttpStatus.OK.getReasonPhrase());
		wrapper.setResponseCode(HttpStatus.OK.value());
		wrapper.setControllerDTOs(objMapper.convertserviceTOstoobjControllerDTOs(serviceTOs));
		return wrapper;
	}
}
