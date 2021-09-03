package com.pro.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.login.dao.CommonDao;
import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.mappers.ShiftsMapper;
import com.pro.login.persistencedto.UserShiftsPersistenceDTO;
import com.pro.login.service.ShiftService;
import com.pro.login.servicedto.UserShiftsServiceDTO;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("unchecked")
@Service("objShiftsServiceImpl")
@Slf4j
public class ShiftsServiceImpl  implements ShiftService{
	
	@Autowired
	@Qualifier("objCommonDaoImpl")
	private CommonDao objCommonDaoImpl;

	@Override
	public List<UserShiftsServiceDTO> getUserShiftsByModule(UserShiftsServiceDTO dataServiceDTO, String strRequestID)
			throws DataNotFoundException {
		ShiftsMapper alertsMapper = new ShiftsMapper();
		List<UserShiftsServiceDTO> commonDataServiceDTOs = null;
		String strQuery = " select * from sp_get_dropdown('ersshifts_ref', 'es_shifttype', ' and es_moduleid = "
				+ dataServiceDTO.getModuleId()
				+ "', ' and es_isactive = true',' and extract(hour from current_timestamp) between extract(hour from es_starttime) and extract(hour from es_endtime)-1', '', '')";
		log.info(strRequestID + ":::::::::::::" + strQuery);

		List<Object[]> list = (List<Object[]>) objCommonDaoImpl.getData(strQuery);
		if (null != list && !list.isEmpty()) {
			List<UserShiftsPersistenceDTO> gisPersDTOs = alertsMapper.conversionForgetUserShiftsByModule(list);
			commonDataServiceDTOs = alertsMapper
					.convertPersistenceDTOtoServiceDTO(gisPersDTOs);
		} else {
			throw new DataNotFoundException(strRequestID + ":::::::No getUserShiftsByModule::::::");
		}
		return commonDataServiceDTOs;
	}

}
