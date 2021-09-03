/**
 * 
 */
package com.pro.login.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.login.dao.CommonDao;
import com.pro.login.mappers.SiteDetailsMapper;
import com.pro.login.persistencedto.SiteDetailsPerstenceDTO;
import com.pro.login.service.SiteValidatorService;
import com.pro.login.servicedto.SiteDetailsServiceTO;

/**
 * @author VENKAT_PRO
 *
 */
@Service("objSiteValidatorServiceImpl")
public class SiteValidatorServiceImpl implements SiteValidatorService {
	@Autowired
	@Qualifier("objCommonDaoImpl")
	private CommonDao objCommonDaoImpl;
	
	@Override
	public List<SiteDetailsServiceTO> getSteDetails(String strReqid, SiteDetailsServiceTO objServiceTO) {
		// TODO Auto-generated method stub
		StringBuilder strQuery=new StringBuilder();
		SiteDetailsMapper detailsMapper=new SiteDetailsMapper();
		strQuery.append("select * from ").append("sp_select_service_site_details('").append(objServiceTO.getSitePublicIp()).append("')");
		@SuppressWarnings("unchecked")
		List<Object[]> data= (List<Object[]>) objCommonDaoImpl.getData(strQuery.toString());
		List<SiteDetailsPerstenceDTO> detailsPerstenceDTOs =detailsMapper.convertListObjectsTodetailsPerstenceDTOs(data);
		List<SiteDetailsServiceTO> detailsServiceTOs=detailsMapper.convertdetailsPerstenceDTOstodetailsServiceTOs(detailsPerstenceDTOs);
		return detailsServiceTOs;
	}

	@Override
	public List<SiteDetailsServiceTO> getSteModuleDetails(String strReqid, SiteDetailsServiceTO objServiceTO) {
		// TODO Auto-generated method stub
		StringBuilder strQuery=new StringBuilder();
		SiteDetailsMapper detailsMapper=new SiteDetailsMapper();
		strQuery.append("select * from ").append("sp_select_site_module_validation(").append(objServiceTO.getSiteId()).append(",").append(objServiceTO.getModuleid()).append(")");
		@SuppressWarnings("unchecked")
		List<Object[]> data= (List<Object[]>) objCommonDaoImpl.getData(strQuery.toString());
		List<SiteDetailsPerstenceDTO> detailsPerstenceDTOs =detailsMapper.convertListObjectsTodetailsPerstenceDTOs(data);
		List<SiteDetailsServiceTO> detailsServiceTOs=detailsMapper.convertdetailsPerstenceDTOstodetailsServiceTOs(detailsPerstenceDTOs);
		return detailsServiceTOs;
	}
 
}
