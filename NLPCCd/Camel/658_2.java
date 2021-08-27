//,temp,sample_7110.java,2,11,temp,sample_7120.java,2,12
//,3
public class xxx {
public static void handleCamelContextStartup(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
boolean skip = "true".equalsIgnoreCase(System.getProperty("skipStartingCamelContext"));
if (skip) {
} else if (testClass.isAnnotationPresent(UseAdviceWith.class)) {
if (testClass.getAnnotation(UseAdviceWith.class).value()) {


log.info("skipping starting camelcontext s as useadvicewith annotation was found and isuseadvicewith is set to true");
}
}
}

};