//,temp,sample_3683.java,2,9,temp,sample_3686.java,2,9
//,2
public class xxx {
protected void testDataFormat(String mainFeature, String dataFormat) throws Exception {
installCamelFeature(mainFeature);
CamelContext camelContext = getOsgiService(bundleContext, CamelContext.class, "(camel.context.name=myCamel)", SERVICE_TIMEOUT);
assertNotNull("Cannot find CamelContext with name myCamel", camelContext);


log.info("getting camel dataformat");
}

};