//,temp,sample_3690.java,2,11,temp,sample_3687.java,2,11
//,3
public class xxx {
protected void testLanguage(String mainFeature, String language) throws Exception {
installCamelFeature(mainFeature);
CamelContext camelContext = getOsgiService(bundleContext, CamelContext.class, "(camel.context.name=myCamel)", 20000);
assertNotNull("Cannot find CamelContext with name myCamel", camelContext);
Language lan = camelContext.resolveLanguage(language);
assertNotNull("Cannot get language with name: " + language, lan);


log.info("found camel language instance with classname");
}

};