//,temp,sample_2960.java,2,9,temp,sample_7119.java,2,9
//,2
public class xxx {
public static void handleCamelContextStartup(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
boolean skip = "true".equalsIgnoreCase(System.getProperty("skipStartingCamelContext"));
if (skip) {


log.info("skipping starting camelcontext s as system property skipstartingcamelcontext is set to be true");
}
}

};