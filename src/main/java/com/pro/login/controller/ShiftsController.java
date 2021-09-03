package com.pro.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.UserShiftsControllerDTO;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.exceptions.InSufficientInputException;
import com.pro.login.mappers.ShiftsMapper;
import com.pro.login.service.impl.ShiftsServiceImpl;
import com.pro.login.servicedto.UserShiftsServiceDTO;
import com.pro.login.utills.IsEmptyUtil;
import com.pro.login.wrappers.ShiftsWrapper;

import lombok.extern.slf4j.Slf4j;

/*
 * @Author deepak
 * @Date: 04-12-2019
 */

@RestController
@RequestMapping("/shifts")
@Slf4j
public class ShiftsController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	@Qualifier("objShiftsServiceImpl")
	private ShiftsServiceImpl objShiftsServiceImpl;
	
	/**
	 * @author : Deepak
	 * @throws: DataNotFoundException
	 * @Date : 2019-12-04
	 * @Des : getUserShiftsByModule
	 * @Parameters : UsersDataControllerDTO dataControllerDTO
	 * @URL : localhost:1000/login/shifts/getUserShiftsByModule
	 */
	@RequestMapping(value = "/getUserShiftsByModule", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response getUserShiftsByModule(@RequestBody UserShiftsControllerDTO dataControllerDTO)
			throws InSufficientInputException, DataNotFoundException {
		
		ShiftsMapper mapper = new ShiftsMapper();
		ShiftsWrapper wrapper = new ShiftsWrapper();
		String strRequestID = request.getAttribute("reqid").toString();
		log.info(strRequestID + "::::getUserShiftsByModule():::::INPUTS ARE::::" + dataControllerDTO.toString());	 		
		if (IsEmptyUtil.isEmptyObject(dataControllerDTO.getModuleId()) && IsEmptyUtil.isEmptyObject(dataControllerDTO.getSiteId())) {
			throw new InSufficientInputException("NO inputs found");	
		}else {
			UserShiftsServiceDTO dataServiceDTO = mapper
					.convertControllerDTOtoServiceDTO(dataControllerDTO);
			List<UserShiftsServiceDTO> sDto = objShiftsServiceImpl.getUserShiftsByModule(dataServiceDTO, strRequestID);
			wrapper.setObjUserShiftsControllerDTO(mapper.convertServiceDTOtoControllerDTOList(sDto));
			wrapper.setResponseCode(org.springframework.http.HttpStatus.OK.value());
			wrapper.setStatus(org.springframework.http.HttpStatus.OK.getReasonPhrase());
		}
		log.info(strRequestID + ":::::OUTPUT:::::" + wrapper.toString());
		return wrapper;
	}

}
