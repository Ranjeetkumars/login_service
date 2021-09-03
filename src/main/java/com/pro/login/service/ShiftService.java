package com.pro.login.service;

import java.util.List;

import com.pro.login.exceptions.DataNotFoundException;
import com.pro.login.servicedto.UserShiftsServiceDTO;



public interface ShiftService {

	
	public List<UserShiftsServiceDTO> getUserShiftsByModule(UserShiftsServiceDTO dataServiceDTO, String strRequestID)
			throws DataNotFoundException;
}
