/**
 * 
 */
package com.pro.login.multitenancy;

/**
 * @author VENKAT_PRO
 *
 */
public class TenantContext {
	private static ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        currentTenant.set(tenant);
    }

    public static String getCurrentTenant() {
        return currentTenant.get();
    }
    public static void clear() {
    	currentTenant.remove();
    }
}
