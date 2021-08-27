//,temp,sample_3684.java,2,11,temp,sample_3689.java,2,9
//,3
public class xxx {
protected void testComponent(String mainFeature, String component) throws Exception {
installCamelFeature(mainFeature);
CamelContext camelContext = getOsgiService(bundleContext, CamelContext.class, "(camel.context.name=myCamel)", SERVICE_TIMEOUT);
assertNotNull("Cannot find CamelContext with name myCamel", camelContext);
Component comp = camelContext.getComponent(component, true, false);
assertNotNull("Cannot get component with name: " + component, comp);


log.info("found camel component instance with classname");
}

};