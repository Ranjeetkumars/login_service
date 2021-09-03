/**
 * 
 */
package com.pro.login.wrappers;

import java.io.Serializable;
import java.util.List;

import com.pro.login.controllerdto.CADLoginControllerDTO;
import com.pro.login.controllerdto.Response;

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
public class CADLoginWrapper extends Response implements Serializable {

	private static final long serialVersionUID = -6039880779273648445L;
	private List<CADLoginControllerDTO> cadLoginControllerDTOs;
}
