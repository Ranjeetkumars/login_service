/**
 * 
 */
package com.pro.login.multitenancy;



import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pro.login.utills.CommonConstants;



/**
 * @author VENKAT_PRO
 *
 */
@Component
public class MapMultiTenantConnectionProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl{

	
	
	private static final long serialVersionUID = -518759023587775266L;
	
	@Autowired
    private Map<String, DataSource> dataSourcesMap;
	
	@Override
	protected DataSource selectAnyDataSource() {
		// TODO Auto-generated method stub
		return dataSourcesMap.get(CommonConstants.DEFAULT_TEANTID);
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		// TODO Auto-generated method stub
		DataSource ds = dataSourcesMap.get(tenantIdentifier);	
		return ds;
	}	
	

}
