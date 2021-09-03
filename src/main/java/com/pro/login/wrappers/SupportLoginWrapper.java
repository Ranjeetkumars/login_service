/**
 * 
 */
package com.pro.login.wrappers;

import java.io.Serializable;
import java.util.List;

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.SupportLoginControllerDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author VENKAT_PRO
 *
 */
@Getter
@Setter
@ToString
public class SupportLoginWrapper extends Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8782783180586901595L;
	private List<SupportLoginControllerDTO> objSupportLoginControllerDTOs;

}
