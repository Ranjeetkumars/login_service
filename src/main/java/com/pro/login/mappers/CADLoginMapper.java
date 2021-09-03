/**
 * 
 */
package com.pro.login.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pro.login.controllerdto.CADLoginControllerDTO;
import com.pro.login.controllerdto.CADLogoutControllerDTO;
import com.pro.login.servicedto.CADLoginServiceDTO;
import com.pro.login.servicedto.CADLogoutServiceDTO;

/**
 * @author VENKAT_PRO
 *
 */
public class CADLoginMapper {

	public CADLoginServiceDTO convertCADLoginControllerDTOtoCADLoginServiceDTO(
			CADLoginControllerDTO objCadLoginControllerDTO) {
		// TODO Auto-generated method stub
		CADLoginServiceDTO objServiceTO=new CADLoginServiceDTO();
		BeanUtils.copyProperties(objCadLoginControllerDTO,objServiceTO);
		return objServiceTO;
	}

	public List<CADLoginControllerDTO> convertcadLoginServiceDTOstoobjCadLoginControllerDTOs(
			List<CADLoginServiceDTO> cadLoginServiceDTOs) {
		// TODO Auto-generated method stub
		List<CADLoginControllerDTO> serdControllerDTOs=new ArrayList<CADLoginControllerDTO>();
		cadLoginServiceDTOs.forEach(serdto->{
			CADLoginControllerDTO cadLoginControllerDTO=new CADLoginControllerDTO();
			BeanUtils.copyProperties(serdto,cadLoginControllerDTO);
			serdControllerDTOs.add(cadLoginControllerDTO);
		});
		return serdControllerDTOs;
	}

	public CADLogoutServiceDTO convertCADLogoutControllerDTOtoCADLogoutServiceDTO(
			CADLogoutControllerDTO objCadLoginControllerDTO) {
		// TODO Auto-generated method stub
		CADLogoutServiceDTO objServiceTO=new CADLogoutServiceDTO();
		BeanUtils.copyProperties(objCadLoginControllerDTO,objServiceTO);
		return objServiceTO;
	}

}
