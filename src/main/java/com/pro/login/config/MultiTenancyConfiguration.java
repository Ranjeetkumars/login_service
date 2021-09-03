/**
 * 
 */
package com.pro.login.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pro.login.multitenancy.CurrentTenantIdentifierResolverImpl;
import com.pro.login.multitenancy.MapMultiTenantConnectionProvider;
import com.pro.login.utills.CommonConstants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;
/**
 * This class defines the data sources to be used for accessing the different
 * databases (one database per tenant). It generates the Hibernate session and
 * entity bean for database access via Spring JPA as well as the Transaction
 * manager to be used.
 * 
 *@author VENKAT_PRO
 * @version 1.0
 * @since 1.0 (June 2019)
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.pro.login.dao" }, transactionManagerRef = "txManager")
@EnableTransactionManagement
@Slf4j
public class MultiTenancyConfiguration {

	@Value("${entitymanager.packagesToScan}")
	private  String ENTITYMANAGER_PACKAGES_TO_SCAN;
	
	//JPA Transaction
	
	@Value("${db.driver}")
	private  String DB_DRIVER;

	@Value("${hibernate.dialect}")
	private  String HIBERNATE_DIALECT;

	@Value("${hibernate.show_sql}")
	private  String HIBERNATE_SHOW_SQL;

	@Value("${hibernate.hbm2ddl.auto}")
	private  String HIBERNATE_HBM2DDL_AUTO;

	//DB Credentials
	
	@Value("${db.url}")
	private  String DB_URL;

	@Value("${db.username}")
	private  String DB_USERNAME;
	
	@Value("${db.password}")
	private  String DB_PASSWORD;
	
	//DB Credentials
	
		@Value("${db.url1}")
		private  String DB_URL1;

		@Value("${db.username1}")
		private  String DB_USERNAME1;
		
		@Value("${db.password1}")
		private  String DB_PASSWORD1;
	
	//HikariCP Properties
	

	
	@Value("${spring.datasource.hikari.maximum-pool-size}")
	private int HIKARI_MAX_POOL_SIZE;
	
	@Value("${spring.datasource.hikari.minimum-idle}")
	private int HIKARI_MIN_IDLE_TIME;
	
	@Value("${spring.datasource.hikari.connection-timeout}")
	private long HIKARI_CONNECTION_TIME_OUT;
	
	@Value("${spring.datasource.hikari.idle-timeout}")
	private long HIKARI_IDLE_TIME_OUT;
	

	@Value("${spring.datasource.hikari.max-lifetime}")
	private long HIKARI_MAX_LIFETIME;
	
	@Value("${spring.datasource.hikari.auto-commit}")
	private boolean HIKARI_AUTO_COMMIT;
	
	

	
	
    /**
     * Builds a map of all data sources defined the application.yml file     * 
     * @return
     */
    @Primary
    @Bean(name = "dataSourcesMtApp")
    public Map<String, DataSource> dataSourcesMtApp() {
        Map<String, DataSource> result = new HashMap<>();      
		
        HikariConfig jdbcConfig = new HikariConfig();
        jdbcConfig.setPoolName("VENKAT");
        
        jdbcConfig.setJdbcUrl(DB_URL);
        jdbcConfig.setUsername(DB_USERNAME);
        jdbcConfig.setPassword(DB_PASSWORD);
        jdbcConfig.setMaximumPoolSize(HIKARI_MAX_POOL_SIZE);
        jdbcConfig.setMinimumIdle(HIKARI_MIN_IDLE_TIME);
        jdbcConfig.setConnectionTimeout(HIKARI_CONNECTION_TIME_OUT);
        jdbcConfig.setIdleTimeout(HIKARI_IDLE_TIME_OUT);
        jdbcConfig.setMaxLifetime(HIKARI_MAX_LIFETIME);
        jdbcConfig.setAutoCommit(HIKARI_AUTO_COMMIT);
        
        jdbcConfig.addDataSourceProperty("cachePrepStmts", true);
        jdbcConfig.addDataSourceProperty("prepStmtCacheSize", 256);
        jdbcConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        jdbcConfig.addDataSourceProperty("useServerPrepStmts", true);
        
        HikariConfig jdbcConfig1 = new HikariConfig();
        jdbcConfig1.setPoolName("VENKAT");
        
        jdbcConfig1.setJdbcUrl(DB_URL1);
        jdbcConfig1.setUsername(DB_USERNAME1);
        jdbcConfig1.setPassword(DB_PASSWORD1);
        
        jdbcConfig1.setMaximumPoolSize(HIKARI_MAX_POOL_SIZE);
        jdbcConfig1.setMinimumIdle(HIKARI_MIN_IDLE_TIME);
        jdbcConfig1.setConnectionTimeout(HIKARI_CONNECTION_TIME_OUT);
        jdbcConfig1.setIdleTimeout(HIKARI_IDLE_TIME_OUT);
        jdbcConfig1.setMaxLifetime(HIKARI_MAX_LIFETIME);
        jdbcConfig1.setAutoCommit(HIKARI_AUTO_COMMIT);
        
        jdbcConfig1.addDataSourceProperty("cachePrepStmts", true);
        jdbcConfig1.addDataSourceProperty("prepStmtCacheSize", 256);
        jdbcConfig1.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        jdbcConfig1.addDataSourceProperty("useServerPrepStmts", true);

        // Add HealthCheck
      //  jdbcConfig.setHealthCheckRegistry(HealthChecks.getHealthCheckRegistry());

        // Add Metrics
       // jdbcConfig.setMetricRegistry(Metrics.registry());
        
        result.put(CommonConstants.AP_TEANTID, new HikariDataSource(jdbcConfig1));
        result.put(CommonConstants.DEFAULT_TEANTID, new HikariDataSource(jdbcConfig));
		log.info(jdbcConfig.toString());
		
		
		
		return result;
    }

    /**
     * Autowires the data sources so that they can be used by the Spring JPA to
     * access the database
     * 
     * @return
     */
    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider() {
        // Autowires dataSourcesMtApp
        return new MapMultiTenantConnectionProvider();
    }

    /**
     * Since this is a multi-tenant application, Hibernate requires that the current
     * tenant identifier is resolved for use with
     * {@link org.hibernate.context.spi.CurrentSessionContext} and
     * {@link org.hibernate.SessionFactory#getCurrentSession()}
     * 
     * @return
     */
    @Bean
    public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
        return new CurrentTenantIdentifierResolverImpl();
    }

    /**
     * org.springframework.beans.factory.FactoryBean that creates a JPA
     * {@link javax.persistence.EntityManagerFactory} according to JPA's standard
     * container bootstrap contract. This is the most powerful way to set up a
     * shared JPA EntityManagerFactory in a Spring application context; the
     * EntityManagerFactory can then be passed to JPA-based DAOs via dependency
     * injection. Note that switching to a JNDI lookup or to a
     * {@link org.springframework.orm.jpa.LocalEntityManagerFactoryBean} definition
     * is just a matter of configuration!
     * 
     * @param multiTenantConnectionProvider
     * @param currentTenantIdentifierResolver
     * @return
     */    
    @Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(MultiTenantConnectionProvider multiTenantConnectionProvider,
		CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
//		hibernateProps.putAll(this.jpaProperties.getProperties());
		hibernateProps.put("hibernate.dialect", HIBERNATE_DIALECT);
		hibernateProps.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		hibernateProps.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

		// No dataSource is set to resulting entityManagerFactoryBean
		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
		result.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setJpaPropertyMap(hibernateProps);

		return result;
	}
    
    
    /**
     * Interface used to interact with the entity manager factory for the
     * persistence unit.
     * 
     * @param entityManagerFactoryBean
     * @return
     */
    @Bean
	public EntityManagerFactory entityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return entityManagerFactoryBean.getObject();
	}

    @Bean
	public SessionFactory getSessionFactory(EntityManagerFactory entityManagerFactory) {
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
    /**
     * Creates a new
     * {@link org.springframework.orm.jpa.JpaTransactionManager#JpaTransactionManager(EntityManagerFactory emf)}
     * instance.
     * 
     * {@link org.springframework.transaction.PlatformTransactionManager} is the
     * central interface in Spring's transaction infrastructure. Applications can
     * use this directly, but it is not primarily meant as API: Typically,
     * applications will work with either TransactionTemplate or declarative
     * transaction demarcation through AOP.
     * 
     * @param entityManagerFactory
     * @return
     */
	@Bean
	public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
}
