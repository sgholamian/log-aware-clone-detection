//,temp,sample_3423.java,2,9,temp,sample_3424.java,2,11
//,3
public class xxx {
public static boolean isOsgiContext(CamelContext camelContext) {
String contextType = camelContext.getClass().getSimpleName();
if (contextType.startsWith("Osgi") || contextType.equals("BlueprintCamelContext")) {


log.info("used assuming running in the osgi container");
}
}

};