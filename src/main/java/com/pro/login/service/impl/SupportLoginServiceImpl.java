/**
 * 
 */
package com.pro.login.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.login.controllerdto.Modules;
import com.pro.login.controllerdto.Roles;
import com.pro.login.dao.CommonDao;
import com.pro.login.jwt.JwtTokenProvider;
import com.pro.login.service.SupportLoginService;
import com.pro.login.servicedto.SupportLoginServiceDTO;
import com.pro.login.utills.LoginResult;

/**
 * @author VENKAT_PRO
 *
 */
@Service("objSupportLoginServiceImpl")
public class SupportLoginServiceImpl implements SupportLoginService {

	@Autowired
	@Qualifier("objCommonDaoImpl")
	private CommonDao objCommonDaoImpl;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Override
	public List<SupportLoginServiceDTO> getLogin(String strReqId, SupportLoginServiceDTO objSupportLoginControllerDTO) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int i = 0;
		List<SupportLoginServiceDTO> objLoginServiceDTOs = new ArrayList<SupportLoginServiceDTO>();
		/*
		 * Login Checking 1.License Checking 2.user is exist or not 3.password match or
		 * not
		 */
		StringBuilder userCheck = new StringBuilder("select * from ").append("sp_login_checkuser_support('")
				.append(objSupportLoginControllerDTO.getUsername()).append("','")
				.append(objSupportLoginControllerDTO.getPassword()).append("',")
				.append(objSupportLoginControllerDTO.getSiteID()).append(")");
		String result_userID = objCommonDaoImpl.getSingleData(userCheck.toString());
		SupportLoginServiceDTO objSupportLoginControllerDTO2 = new SupportLoginServiceDTO();
		switch (result_userID) {
		case "Max Limit For Login Exceeded":
			objSupportLoginControllerDTO2.setErrorCode(LoginResult.MAX_LIMIT_LOGIN_EXCEEDED.getErrorcode().toString());
			objSupportLoginControllerDTO2.setErrorName(LoginResult.MAX_LIMIT_LOGIN_EXCEEDED.getLoginResult());
			break;
		case "User Not Found":
			objSupportLoginControllerDTO2.setErrorCode(LoginResult.USERNOT_FOUND.getErrorcode().toString());
			objSupportLoginControllerDTO2.setErrorName(LoginResult.USERNOT_FOUND.getLoginResult());
			break;
		case "Password do not match":
			objSupportLoginControllerDTO2.setErrorCode(LoginResult.PASSWORDNOTFOUND.getErrorcode().toString());
			objSupportLoginControllerDTO2.setErrorName(LoginResult.PASSWORDNOTFOUND.getLoginResult());
			break;

		default:
			objSupportLoginControllerDTO2.setUserID(result_userID);
			objSupportLoginControllerDTO2.setUsername(objSupportLoginControllerDTO.getUsername());
			objSupportLoginControllerDTO2.setSiteID(objSupportLoginControllerDTO.getSiteID());
			StringBuilder Userdetails = new StringBuilder(
					"select us_fname,us_lname,us_imagepath from amsusers_ref where us_userid =").append(result_userID);
			@SuppressWarnings("unchecked")
			List<Object[]> udet = (List<Object[]>) objCommonDaoImpl.getData(Userdetails.toString());
			if (udet != null) {
				udet.forEach(obj -> {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:0:"+obj[0]);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:1:"+obj[1]);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@:2:"+obj[1]);
					objSupportLoginControllerDTO2.setName(obj[0].toString() + obj[1].toString());
					objSupportLoginControllerDTO2.setImgepath(obj[2].toString());
				});
			}
			// get All modules
			StringBuilder module_check = new StringBuilder("select * from ").append("sp_login_userwise_modules(")
					.append(result_userID).append(")");
			@SuppressWarnings("unchecked")
			List<Object[]> modules = (List<Object[]>) objCommonDaoImpl.getData(module_check.toString());
			List<Modules> listModule = new ArrayList<Modules>();
			List<Roles> arryRoles = new ArrayList<Roles>();
			List<String> tockenroles = new ArrayList<String>();
			StringBuilder strRoles = new StringBuilder();
			if (modules != null && modules.size() > 0) {
				modules.forEach(mod -> {
					Modules Objmodules = new Modules();
					Objmodules.setModuleid(mod[0].toString());
					Objmodules.setModuleName(mod[1].toString());
					listModule.add(Objmodules);
					// Roles Checking
					StringBuilder roles = new StringBuilder("select * from ").append("sp_login_assignedroles(")
							.append(result_userID).append(",").append(mod[0].toString()).append(")");
					@SuppressWarnings("unchecked")
					List<Object[]> rolesArray = (List<Object[]>) objCommonDaoImpl.getData(roles.toString());
					if (rolesArray != null && rolesArray.size() > 0) {

						rolesArray.forEach(role -> {
							Roles rols = new Roles();
							rols.setModuleID(mod[0].toString());
							rols.setRoleID(role[0].toString());
							rols.setRoleName(role[1].toString());
							tockenroles.add(role[1].toString());
							arryRoles.add(rols);
							strRoles.append(role[1].toString() + "$");
						});
					} else {
						objSupportLoginControllerDTO2
								.setErrorCode(LoginResult.ROLE_NOT_FOUND.getErrorcode().toString());
						objSupportLoginControllerDTO2.setErrorName(LoginResult.ROLE_NOT_FOUND.getLoginResult());
					}

				});

			} else {
				objSupportLoginControllerDTO2.setErrorCode(LoginResult.MODULE_NOT_FOUND.getErrorcode().toString());
				objSupportLoginControllerDTO2.setErrorName(LoginResult.MODULE_NOT_FOUND.getLoginResult());
			}

			objSupportLoginControllerDTO2.setRoles(arryRoles);
			objSupportLoginControllerDTO2.setModules(listModule);

			String Tocken = jwtTokenProvider.createToken(objSupportLoginControllerDTO2.getUsername(), tockenroles);
			objSupportLoginControllerDTO2.setTockenID(Tocken);
			isertTockenDetails(objSupportLoginControllerDTO2.getUsername(), objSupportLoginControllerDTO.getPassword(),
					strRoles.toString(), Tocken, objSupportLoginControllerDTO2.getUserID(), "1",
					objSupportLoginControllerDTO2.getSiteID());
			StringBuilder loginDetails = new StringBuilder("select * from sp_ins_amslogin_trans(")
					.append(objSupportLoginControllerDTO2.getUserID()).append(",'")
					.append(objSupportLoginControllerDTO.getLocalIp()).append("',").append("1000").append(",")
					.append('0').append(",").append('1').append(",").append(objSupportLoginControllerDTO2.getSiteID())
					.append(",").append('1').append(",").append(objSupportLoginControllerDTO.getCeneterid())
					.append(")");			
			
			objCommonDaoImpl.saveData(loginDetails.toString());

			break;
		}
		objLoginServiceDTOs.add(objSupportLoginControllerDTO2);
		return objLoginServiceDTOs;
	}

	public void isertTockenDetails(String username, String password, String strRoles, String tocken, String userid,
			String moduleid, String siteid) {
		StringBuilder insertTocken = new StringBuilder("select * from sp_ins_user_authentication('").append(username)
				.append("','").append(password).append("','").append(strRoles).append("','").append(tocken).append("',")
				.append(userid).append(",").append(moduleid).append(",").append(siteid).append(")");
		objCommonDaoImpl.saveData(insertTocken.toString());
	}

}
