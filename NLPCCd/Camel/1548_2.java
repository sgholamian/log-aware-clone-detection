//,temp,sample_7121.java,2,14,temp,sample_2961.java,2,12
//,3
public class xxx {
protected void handleCamelContextStartup(GenericApplicationContext context, Class<?> testClass) throws Exception {
boolean skip = "true".equalsIgnoreCase(System.getProperty("skipStartingCamelContext"));
if (skip) {
} else if (testClass.isAnnotationPresent(UseAdviceWith.class)) {
if (testClass.getAnnotation(UseAdviceWith.class).value()) {


log.info("skipping starting camelcontext s as useadvicewith annotation was found and isuseadvicewith is set to true");
}
}
}

};