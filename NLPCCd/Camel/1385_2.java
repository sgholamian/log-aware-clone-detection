//,temp,sample_6729.java,2,11,temp,sample_6727.java,2,9
//,3
public class xxx {
public static void osgiUpdate(DefaultCamelContext camelContext, BundleContext bundleContext) {
ObjectHelper.notNull(bundleContext, "BundleContext");
camelContext.setNameStrategy(new OsgiCamelContextNameStrategy(bundleContext));
camelContext.setManagementNameStrategy(new OsgiManagementNameStrategy(camelContext, bundleContext));


log.info("using osgiclassresolver");
}

};