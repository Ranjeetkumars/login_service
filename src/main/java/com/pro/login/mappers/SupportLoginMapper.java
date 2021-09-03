/**
 * 
 */
package com.pro.login.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pro.login.controllerdto.SupportLoginControllerDTO;
import com.pro.login.servicedto.SupportLoginServiceDTO;

/**
 * @author VENKAT_PRO
 *
 */
public class SupportLoginMapper {

	public SupportLoginServiceDTO convertCADLoginControllerDTOtoCADLoginServiceDTO(
			SupportLoginControllerDTO objSupportLoginControllerDTO) {
		// TODO Auto-generated method stub
		SupportLoginServiceDTO objServiceTO=new SupportLoginServiceDTO();
		BeanUtils.copyProperties(objSupportLoginControllerDTO,objServiceTO);
		return objServiceTO;
	}

	public List<SupportLoginControllerDTO> convertSupportLoginServiceDTOstoobjSupportLoginControllerDTOs(
			List<SupportLoginServiceDTO> cadLoginServiceDTOs) {
		// TODO Auto-generated method stub
		List<SupportLoginControllerDTO> serdControllerDTOs=new ArrayList<SupportLoginControllerDTO>();
		cadLoginServiceDTOs.forEach(serdto->{
			SupportLoginControllerDTO cadLoginControllerDTO=new SupportLoginControllerDTO();
			BeanUtils.copyProperties(serdto,cadLoginControllerDTO);
			serdControllerDTOs.add(cadLoginControllerDTO);
		});
		return serdControllerDTOs;
	}

}
