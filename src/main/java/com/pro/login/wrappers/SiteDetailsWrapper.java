/**
 * 
 */
package com.pro.login.wrappers;

import java.io.Serializable;
import java.util.List;

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.SiteDetailsControllerDTO;

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
public class SiteDetailsWrapper extends Response implements Serializable {
	private static final long serialVersionUID = -6237707314711990470L;
	private List<SiteDetailsControllerDTO> controllerDTOs;
}
