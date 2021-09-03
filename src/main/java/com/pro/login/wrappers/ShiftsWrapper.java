/**
 * 
 */
package com.pro.login.wrappers;

import java.io.Serializable;
import java.util.List;

import com.pro.login.controllerdto.Response;
import com.pro.login.controllerdto.UserShiftsControllerDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Deepak
 *@Date: 04-12-2019
 */
@Getter
@Setter
@ToString
public class ShiftsWrapper extends Response implements Serializable {

	private static final long serialVersionUID = -6039880779273648445L;
	private List<UserShiftsControllerDTO> objUserShiftsControllerDTO;
}
