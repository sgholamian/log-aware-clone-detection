//,temp,sample_6728.java,2,10,temp,sample_6732.java,2,14
//,3
public class xxx {
public static void osgiUpdate(DefaultCamelContext camelContext, BundleContext bundleContext) {
ObjectHelper.notNull(bundleContext, "BundleContext");
camelContext.setNameStrategy(new OsgiCamelContextNameStrategy(bundleContext));
camelContext.setManagementNameStrategy(new OsgiManagementNameStrategy(camelContext, bundleContext));
camelContext.setClassResolver(new OsgiClassResolver(camelContext, bundleContext));
camelContext.setFactoryFinderResolver(new OsgiFactoryFinderResolver(bundleContext));
camelContext.setPackageScanClassResolver(new OsgiPackageScanClassResolver(bundleContext));
camelContext.setComponentResolver(new OsgiComponentResolver(bundleContext));
camelContext.setLanguageResolver(new OsgiLanguageResolver(bundleContext));


log.info("using osgidataformatresolver");
}

};