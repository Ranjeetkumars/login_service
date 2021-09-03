/**
 * 
 */
package com.pro.login.dao;

import java.util.List;


/**
 * @author VENKAT_PRO
 *
 */
public interface CommonDao {
	public List<?> getData(String strQuery);	

	public String saveData(String strQuery);

	public String getSingleData(String strQuery);
}
