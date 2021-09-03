package com.pro.login.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pro.login.controllerdto.UserShiftsControllerDTO;
import com.pro.login.persistencedto.UserShiftsPersistenceDTO;
import com.pro.login.servicedto.UserShiftsServiceDTO;



public class ShiftsMapper {

	public UserShiftsServiceDTO convertControllerDTOtoServiceDTO(UserShiftsControllerDTO controllerDTO) {
		UserShiftsServiceDTO propertiesServiceDTO = new UserShiftsServiceDTO();
		BeanUtils.copyProperties(controllerDTO,propertiesServiceDTO);
		return propertiesServiceDTO;
	}
	
	public List<UserShiftsServiceDTO> convertPersistenceDTOtoServiceDTO(
			List<UserShiftsPersistenceDTO> persistenceDTOs) {
		List<UserShiftsServiceDTO> commonDataServiceDTOs = new ArrayList<UserShiftsServiceDTO>();
		for (UserShiftsPersistenceDTO objects : persistenceDTOs) {
			UserShiftsServiceDTO gisCommonDataServiceDTO = new UserShiftsServiceDTO();
			BeanUtils.copyProperties(objects,gisCommonDataServiceDTO);
			commonDataServiceDTOs.add(gisCommonDataServiceDTO);
		}
		return commonDataServiceDTOs;
	}
	
	public List<UserShiftsControllerDTO> convertServiceDTOtoControllerDTOList(List<UserShiftsServiceDTO> dataServiceDTOs) {
		List<UserShiftsControllerDTO> controllerDTOs = new ArrayList<UserShiftsControllerDTO>();
		for (UserShiftsServiceDTO objects : dataServiceDTOs) {
			UserShiftsControllerDTO dataControllerDTO = new UserShiftsControllerDTO();
			BeanUtils.copyProperties(objects,dataControllerDTO);
			controllerDTOs.add(dataControllerDTO);
		}
		return controllerDTOs;
	}
	
	public List<UserShiftsPersistenceDTO> conversionForgetUserShiftsByModule(List<Object[]> list) {
		List<UserShiftsPersistenceDTO> commonDataPersistenceDTOs = new ArrayList<UserShiftsPersistenceDTO>();
		for (Object[] objects : list) {
			UserShiftsPersistenceDTO objCommonDataPersistenceDTO = new UserShiftsPersistenceDTO();
			if (objects[0] != null) {
				objCommonDataPersistenceDTO.setShiftId(objects[0].toString());
			} else {
				objCommonDataPersistenceDTO.setShiftId(com.pro.login.utills.CommonConstants.DATA_NOT_AVIALABLE);
			}
			if (objects[1] != null) {
				objCommonDataPersistenceDTO.setShiftType(objects[1].toString());
			} else {
				objCommonDataPersistenceDTO.setShiftType(com.pro.login.utills.CommonConstants.DATA_NOT_AVIALABLE);
			}
			
			commonDataPersistenceDTOs.add(objCommonDataPersistenceDTO);
		}
		return commonDataPersistenceDTOs;
	}
	
	
}
