package com.pro.login.controllerdto;
/* 
 * @author VENKAT
 * @since 2019-03-30
 * @Time 3:58PM
 * 
 */

import java.util.List;

import lombok.Data;

/* @author VENKAT
* @since 2019-03-30
* @Time 3:58PM
*
*/
@Data
public class Response {
	String status;
	Integer responseCode;
	String Count;
	List<String> errorsMsgs;	
	String output;
	String serverIp;
	String port;
}
