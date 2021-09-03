
package com.pro.login.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pro.login.controllerdto.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Deepak
 *
 */
@RestController
@RequestMapping("/co/ip")
@Slf4j
public class IPController {
	
	
	@Autowired
	private HttpServletRequest request;
	@SuppressWarnings("unused")

	@Autowired
	private Environment environment;
	
	@RequestMapping(value = "/getIP", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getIP() {	
		Response res=new Response();
		log.info(request.getRemoteAddr());
		res.setServerIp(request.getRemoteAddr());
		//Added By Ranjeet Kr. Sinhas
		//res.setServerIp(InetAddress.getLocalHost().getHostAddress());
		//res.setPort(environment.getProperty("local.server.port"));
		return res;
	}
}
