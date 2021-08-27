//,temp,sample_7121.java,2,14,temp,sample_2961.java,2,12
//,3
public class xxx {
public static void handleCamelContextStartup(ConfigurableApplicationContext context, Class<?> testClass) throws Exception {
boolean skip = "true".equalsIgnoreCase(System.getProperty("skipStartingCamelContext"));
if (skip) {
} else if (testClass.isAnnotationPresent(UseAdviceWith.class)) {
if (testClass.getAnnotation(UseAdviceWith.class).value()) {
skip = true;
} else {


log.info("starting camelcontext s as useadvicewith annotation was found but isuseadvicewith is set to false");
}
}
}

};