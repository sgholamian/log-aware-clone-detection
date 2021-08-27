//,temp,sample_6731.java,2,13,temp,sample_6730.java,2,12
//,3
public class xxx {
public static void osgiUpdate(DefaultCamelContext camelContext, BundleContext bundleContext) {
ObjectHelper.notNull(bundleContext, "BundleContext");
camelContext.setNameStrategy(new OsgiCamelContextNameStrategy(bundleContext));
camelContext.setManagementNameStrategy(new OsgiManagementNameStrategy(camelContext, bundleContext));
camelContext.setClassResolver(new OsgiClassResolver(camelContext, bundleContext));
camelContext.setFactoryFinderResolver(new OsgiFactoryFinderResolver(bundleContext));
camelContext.setPackageScanClassResolver(new OsgiPackageScanClassResolver(bundleContext));


log.info("using osgicomponentresolver");
}

};