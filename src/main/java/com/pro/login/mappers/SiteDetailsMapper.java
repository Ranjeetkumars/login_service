/**
 * 
 */
package com.pro.login.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pro.login.controllerdto.SiteDetailsControllerDTO;
import com.pro.login.persistencedto.SiteDetailsPerstenceDTO;
import com.pro.login.servicedto.SiteDetailsServiceTO;
import com.pro.login.utills.CommonConstants;

/**
 * @author VENKAT_PRO
 *
 */
public class SiteDetailsMapper {

	public SiteDetailsServiceTO convertSiteDetailsControllerDTOtoSiteDetailsServiceDTO(
			SiteDetailsControllerDTO objControllerDTO) {		
		// TODO Auto-generated method stub
		SiteDetailsServiceTO objServiceTO=new SiteDetailsServiceTO();
		BeanUtils.copyProperties(objControllerDTO,objServiceTO);
		return objServiceTO;
	}

	public List<SiteDetailsPerstenceDTO> convertListObjectsTodetailsPerstenceDTOs(List<Object[]> data) {
		// TODO Auto-generated method stub
		List<SiteDetailsPerstenceDTO> detailsPerstenceDTOs=new ArrayList<SiteDetailsPerstenceDTO>();
		data.forEach(obj->{	
			SiteDetailsPerstenceDTO objPerstenceDTO=new SiteDetailsPerstenceDTO();			
			if (obj[0] != null) {
				objPerstenceDTO.setServiceProviderId(obj[0].toString());
			}else {
				objPerstenceDTO.setServiceProviderId(obj[0].toString());
			}
			if (obj[1] != null) {
				objPerstenceDTO.setServiceProviderName(obj[1].toString());
			}else {
				objPerstenceDTO.setServiceProviderName(CommonConstants.DATA_NOT_AVIALABLE);
			}
			if (obj[2] != null) {
				objPerstenceDTO.setSiteId(obj[2].toString());
			}else {
				objPerstenceDTO.setSiteId(CommonConstants.DATA_NOT_AVIALABLE);
			}
			if (obj[3] != null) {
				objPerstenceDTO.setSiteName(obj[3].toString());
			}else {
				objPerstenceDTO.setSiteName(CommonConstants.DATA_NOT_AVIALABLE);
			}
			if (obj[4] != null) {
				objPerstenceDTO.setStartDate(obj[4].toString());
			}else {
				objPerstenceDTO.setStartDate(CommonConstants.DATA_NOT_AVIALABLE);
			}
			if (obj[5] != null) {
				objPerstenceDTO.setEndDate(obj[5].toString());
			}else {
				objPerstenceDTO.setEndDate(CommonConstants.DATA_NOT_AVIALABLE);
			}
			
			detailsPerstenceDTOs.add(objPerstenceDTO);
		});
		return detailsPerstenceDTOs;
	}

	public List<SiteDetailsServiceTO> convertdetailsPerstenceDTOstodetailsServiceTOs(
			List<SiteDetailsPerstenceDTO> detailsPerstenceDTOs) {
		List<SiteDetailsServiceTO> detailsServiceTOs=new ArrayList<SiteDetailsServiceTO>();
		// TODO Auto-generated method stub
		detailsPerstenceDTOs.forEach(perDto->{
			SiteDetailsServiceTO detailsServiceTO=new SiteDetailsServiceTO();
			BeanUtils.copyProperties(perDto,detailsServiceTO);
			detailsServiceTOs.add(detailsServiceTO);
		});
		return detailsServiceTOs;
	}

	public List<SiteDetailsControllerDTO> convertserviceTOstoobjControllerDTOs(List<SiteDetailsServiceTO> serviceTOs) {
		// TODO Auto-generated method stub
		List<SiteDetailsControllerDTO> controllerDTOs=new ArrayList<SiteDetailsControllerDTO>();
		serviceTOs.forEach(service->{
			SiteDetailsControllerDTO  controllerDTO=new SiteDetailsControllerDTO();
			BeanUtils.copyProperties(service,controllerDTO);
			controllerDTOs.add(controllerDTO);
		});
		return controllerDTOs;
	}

}
