//,temp,sample_3684.java,2,11,temp,sample_3689.java,2,9
//,3
public class xxx {
protected void testLanguage(String mainFeature, String language) throws Exception {
installCamelFeature(mainFeature);
CamelContext camelContext = getOsgiService(bundleContext, CamelContext.class, "(camel.context.name=myCamel)", 20000);
assertNotNull("Cannot find CamelContext with name myCamel", camelContext);


log.info("getting camel language");
}

};