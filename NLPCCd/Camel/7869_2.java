//,temp,sample_3690.java,2,11,temp,sample_3687.java,2,11
//,3
public class xxx {
protected void testDataFormat(String mainFeature, String dataFormat) throws Exception {
installCamelFeature(mainFeature);
CamelContext camelContext = getOsgiService(bundleContext, CamelContext.class, "(camel.context.name=myCamel)", SERVICE_TIMEOUT);
assertNotNull("Cannot find CamelContext with name myCamel", camelContext);
DataFormat df = camelContext.resolveDataFormat(dataFormat);
assertNotNull("Cannot get dataformat with name: " + dataFormat, df);


log.info("found camel dataformat instance with classname");
}

};