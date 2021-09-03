/**
 * 
 */
package com.pro.login.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pro.login.dao.CommonDao;
import com.pro.login.multitenancy.TenantContext;
import com.pro.login.utills.CommonConstants;

/**
 * @author VENKAT_PRO
 */
@Repository("objCommonDaoImpl")
@Transactional
public class CommonDaoImpl implements CommonDao{
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getData(String strQuery) {
		TenantContext.setCurrentTenant(CommonConstants.DEFAULT_TEANTID);
		Session session = null;
		List<Object[]> listData = null;
		session = sessionFactory.withOptions().tenantIdentifier(TenantContext.getCurrentTenant().toString())
				.openSession();
		listData = session.createSQLQuery(strQuery).list();
		session.close();
		return listData;
	}

	@Override
	public String saveData(String strQuery) {
		Session session = null;
		String strResult = null;
		session = sessionFactory.withOptions().tenantIdentifier(TenantContext.getCurrentTenant().toString())
				.openSession();
		strResult = session.createSQLQuery(strQuery).list().get(0).toString();
		session.close();
		return strResult;
	}

	@Override
	public String getSingleData(String strQuery){
		Session session = null;
		String listData = null;
		session = sessionFactory.withOptions().tenantIdentifier(TenantContext.getCurrentTenant().toString())
				.openSession();
		@SuppressWarnings("rawtypes")
		List data = session.createSQLQuery(strQuery).list();
		if (data == null || data.isEmpty() || data.get(0) == null) {
			
		} else {
			listData = data.get(0).toString();
		}
		session.close();
		return listData;
	}
	
}
