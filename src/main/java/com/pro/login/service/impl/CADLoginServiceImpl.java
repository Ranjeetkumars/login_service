/**
 * 
 */
package com.pro.login.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pro.login.controllerdto.Roles;
import com.pro.login.dao.CommonDao;
import com.pro.login.jwt.JwtTokenProvider;
import com.pro.login.multitenancy.TenantContext;
import com.pro.login.service.CADLoginService;
import com.pro.login.servicedto.CADLoginServiceDTO;
import com.pro.login.servicedto.CADLogoutServiceDTO;
import com.pro.login.utills.CommonConstants;
import com.pro.login.utills.LoginResult;

/**
 * @author VENKAT_PRO
 *
 */
@Service("objCADLoginServiceImpl")
public class CADLoginServiceImpl implements CADLoginService {

	@Autowired
	@Qualifier("objCommonDaoImpl")
	private CommonDao objCommonDaoImpl;
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@SuppressWarnings("null")
	@Override
	public List<CADLoginServiceDTO> getLogin(String strReqId, CADLoginServiceDTO objCadLoginServiceDTO) {
		// TODO Auto-generated method stub
		int i = 0;
		List<CADLoginServiceDTO> cadLoginServiceDTOs = new ArrayList<CADLoginServiceDTO>();
		/*
		 * Login Checking 1.License Checking 2.user is exist or not 3.password match or
		 * not
		 */
		StringBuilder userCheck = new StringBuilder("select * from ").append("sp_login_checkuser('")
				.append(objCadLoginServiceDTO.getUsername()).append("','").append(objCadLoginServiceDTO.getPassword())
				.append("',").append(objCadLoginServiceDTO.getModuleid()).append(",")
				.append(objCadLoginServiceDTO.getSiteID()).append(")");
		String result_userID = objCommonDaoImpl.getSingleData(userCheck.toString());
		CADLoginServiceDTO objCadLoginServiceDTO2 = new CADLoginServiceDTO();
		switch (result_userID) {
		case "Max Limit For Login Exceeded":
			objCadLoginServiceDTO2.setErrorCode(LoginResult.MAX_LIMIT_LOGIN_EXCEEDED.getErrorcode().toString());
			objCadLoginServiceDTO2.setErrorName(LoginResult.MAX_LIMIT_LOGIN_EXCEEDED.getLoginResult());
			break;
		case "User Not Found":
			objCadLoginServiceDTO2.setErrorCode(LoginResult.USERNOT_FOUND.getErrorcode().toString());
			objCadLoginServiceDTO2.setErrorName(LoginResult.USERNOT_FOUND.getLoginResult());
			break;
		case "Password do not match":
			objCadLoginServiceDTO2.setErrorCode(LoginResult.PASSWORDNOTFOUND.getErrorcode().toString());
			objCadLoginServiceDTO2.setErrorName(LoginResult.PASSWORDNOTFOUND.getLoginResult());
			break;

		default:
			objCadLoginServiceDTO2.setUserID(result_userID);
			objCadLoginServiceDTO2.setUsername(objCadLoginServiceDTO.getUsername());
			objCadLoginServiceDTO2.setModuleid(objCadLoginServiceDTO.getModuleid());
			objCadLoginServiceDTO2.setModuleName(objCadLoginServiceDTO.getModuleName());
			objCadLoginServiceDTO2.setSiteID(objCadLoginServiceDTO.getSiteID());
			
			StringBuilder Userdetails=new StringBuilder("select us_fname,us_lname,us_imagepath from amsusers_ref where us_userid =").append(result_userID);
			@SuppressWarnings("unchecked") List<Object[]> udet=(List<Object[]>) objCommonDaoImpl.getData(Userdetails.toString());
			if(udet!=null) {
				udet.forEach(obj->{
					if(obj[1]!=null)
					objCadLoginServiceDTO2.setName(obj[0].toString()+obj[1].toString());
					if(obj[2]!=null)
					objCadLoginServiceDTO2.setImgepath(obj[2].toString());
				});
			}
			StringBuilder allreadyLogin = new StringBuilder("select * from sp_login_checkuser_allreadylogin(")
					.append(result_userID).append(",").append(objCadLoginServiceDTO.getSiteID()).append(",")
					.append(objCadLoginServiceDTO.getModuleid()).append(")");
			String result_msg = objCommonDaoImpl.getSingleData(allreadyLogin.toString());
			switch (result_msg) {
			case "User Is Allready Login With Another System":
				objCadLoginServiceDTO2.setErrorCode(LoginResult.USER_IS_ALLREADY_LOGIN.getErrorcode().toString());
				objCadLoginServiceDTO2.setErrorName(LoginResult.USER_IS_ALLREADY_LOGIN.getLoginResult());
				break;
			case "User Is Allready login With Another Module":
				objCadLoginServiceDTO2
						.setErrorCode(LoginResult.USER_IS_LOGIN_WITH_OTHERMODULE.getErrorcode().toString());
				objCadLoginServiceDTO2.setErrorName(LoginResult.USER_IS_LOGIN_WITH_OTHERMODULE.getLoginResult());
				break;
			case "Not Login In Any System":
				StringBuilder telephony = new StringBuilder("select * from ").append("sp_getcti_credentials(")
						.append(result_userID).append(",").append(objCadLoginServiceDTO.getModuleid()).append(")");
				@SuppressWarnings("unchecked")
				List<Object[]> telephoneCred = (List<Object[]>) objCommonDaoImpl.getData(telephony.toString());
				if (telephoneCred == null && telephoneCred.size() <= 0) {
					objCadLoginServiceDTO2.setErrorCode(LoginResult.CTI_NOT_MAPPED.getErrorcode().toString());
					objCadLoginServiceDTO2.setErrorName(LoginResult.CTI_NOT_MAPPED.getLoginResult());
				} else {
					StringBuilder terminal = new StringBuilder("select * from ").append("sp_getterminal_credentials('")
							.append(objCadLoginServiceDTO.getLocalIp()).append("',")
							.append(objCadLoginServiceDTO.getSiteID()).append(")");
					@SuppressWarnings("unchecked")
					List<Object[]> terminalarray = (List<Object[]>) objCommonDaoImpl.getData(terminal.toString());
					if (terminalarray != null && terminalarray.size() > 0) {
						terminalarray.forEach(ter -> {
							objCadLoginServiceDTO2.setCti_Extenstion(ter[0].toString());
							objCadLoginServiceDTO2.setCti_Extenstion_password(ter[1].toString());
						});
						telephoneCred.forEach(tel -> {
							objCadLoginServiceDTO2.setCti_username(tel[1].toString());
							objCadLoginServiceDTO2.setCti_userid(tel[0].toString());
						});
						// Modules Checking
						StringBuilder module_check = new StringBuilder("select * from ")
								.append("sp_login_checkformodule(").append(result_userID).append(",")
								.append(objCadLoginServiceDTO.getModuleid()).append(")");
						@SuppressWarnings("unchecked")
						List<Object[]> modules = (List<Object[]>) objCommonDaoImpl.getData(module_check.toString());
						if (modules != null && modules.size() > 0) {

							modules.forEach(mod -> {
								objCadLoginServiceDTO2.setModuleid(mod[0].toString());
								objCadLoginServiceDTO2.setModuleName(mod[1].toString());
								// Roles Checking
								StringBuilder roles = new StringBuilder("select * from ")
										.append("sp_login_assignedroles(").append(result_userID).append(",")
										.append(objCadLoginServiceDTO.getModuleid()).append(")");
								@SuppressWarnings("unchecked")
								List<Object[]> rolesArray = (List<Object[]>) objCommonDaoImpl.getData(roles.toString());
								if (rolesArray != null && rolesArray.size() > 0) {
									List<Roles> arryRoles = new ArrayList<Roles>();
									StringBuilder strRoles = new StringBuilder();
									List<String> tockenroles = new ArrayList<String>();
									rolesArray.forEach(role -> {
										Roles rols = new Roles();
										rols.setModuleID(mod[0].toString());
										rols.setRoleID(role[0].toString());
										rols.setRoleName(role[1].toString());
										tockenroles.add(role[1].toString());
										arryRoles.add(rols);
										strRoles.append(role[1].toString() + "$");
									});
									objCadLoginServiceDTO2.setRoles(arryRoles);
									String Tocken = jwtTokenProvider.createToken(objCadLoginServiceDTO2.getUsername(),
											tockenroles);
									objCadLoginServiceDTO2.setTockenID(Tocken);
									isertTockenDetails(objCadLoginServiceDTO2.getUsername(),
											objCadLoginServiceDTO.getPassword(), strRoles.toString(), Tocken,
											objCadLoginServiceDTO2.getUserID(), objCadLoginServiceDTO2.getModuleid(),
											objCadLoginServiceDTO2.getSiteID());
									StringBuilder loginDetails = new StringBuilder(
											"select * from sp_ins_amslogin_trans(")
													.append(objCadLoginServiceDTO2.getUserID()).append(",'")
													.append(objCadLoginServiceDTO.getLocalIp()).append("',")
													.append("1000").append(",")
													.append(objCadLoginServiceDTO2.getCti_Extenstion()).append(",")
													.append(objCadLoginServiceDTO2.getModuleid()).append(",")
													.append(objCadLoginServiceDTO2.getSiteID()).append(",")
													.append(objCadLoginServiceDTO.getShiftId()).append(",")													
													.append(objCadLoginServiceDTO.getCeneterid())													
													.append(")");									
									objCommonDaoImpl.saveData(loginDetails.toString());
									TenantContext.setCurrentTenant(CommonConstants.AP_TEANTID);
									objCommonDaoImpl.saveData(loginDetails.toString());
									TenantContext.setCurrentTenant(CommonConstants.DEFAULT_TEANTID);									
								} else {
									objCadLoginServiceDTO2
											.setErrorCode(LoginResult.ROLE_NOT_FOUND.getErrorcode().toString());
									objCadLoginServiceDTO2.setErrorName(LoginResult.ROLE_NOT_FOUND.getLoginResult());
								}
							});

						} else {
							objCadLoginServiceDTO2.setErrorCode(LoginResult.MODULE_NOT_FOUND.getErrorcode().toString());
							objCadLoginServiceDTO2.setErrorName(LoginResult.MODULE_NOT_FOUND.getLoginResult());
						}
					} else {
						objCadLoginServiceDTO2.setErrorCode(LoginResult.TERMINAL_NOT_MAPPED.getErrorcode().toString());
						objCadLoginServiceDTO2.setErrorName(LoginResult.TERMINAL_NOT_MAPPED.getLoginResult());
					}

				}
				break;
			}
			break;
		}
		cadLoginServiceDTOs.add(objCadLoginServiceDTO2);
		return cadLoginServiceDTOs;
	}

	public void isertTockenDetails(String username, String password, String strRoles, String tocken, String userid,
			String moduleid, String siteid) {
		StringBuilder insertTocken = new StringBuilder("select * from sp_ins_user_authentication('").append(username)
				.append("','").append(password).append("','").append(strRoles).append("','").append(tocken).append("',")
				.append(userid).append(",").append(moduleid).append(",").append(siteid).append(")");
		objCommonDaoImpl.saveData(insertTocken.toString());
	}

	public String getLogout(String strRequestID, CADLogoutServiceDTO cadLoginServiceDTO) {
		// TODO Auto-generated method stub
		StringBuilder logout=new StringBuilder("select * from sp_update_amslogin_trans_logouttime('").append(cadLoginServiceDTO.getLogouttime()).append("',").append(cadLoginServiceDTO.getUserid()).append(",").append(cadLoginServiceDTO.getSiteid()).append(")");
		TenantContext.setCurrentTenant(CommonConstants.AP_TEANTID);
		objCommonDaoImpl.saveData(logout.toString());
		TenantContext.setCurrentTenant(CommonConstants.DEFAULT_TEANTID);
		return objCommonDaoImpl.saveData(logout.toString());
	}
}
