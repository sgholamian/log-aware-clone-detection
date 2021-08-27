//,temp,sample_2952.java,2,13,temp,sample_2953.java,2,15
//,3
public class xxx {
protected void handleDisableJmx(GenericApplicationContext context, Class<?> testClass) {
CamelSpringTestHelper.setOriginalJmxDisabledValue(System.getProperty(JmxSystemPropertyKeys.DISABLED));
if (testClass.isAnnotationPresent(DisableJmx.class)) {
if (testClass.getAnnotation(DisableJmx.class).value()) {
System.setProperty(JmxSystemPropertyKeys.DISABLED, "true");
} else {
System.clearProperty(JmxSystemPropertyKeys.DISABLED);
}
} else if (!testClass.isAnnotationPresent(EnableRouteCoverage.class)) {


log.info("disabling camel jmx globally for tests by default use the disablejmx annotation to override the default setting");
}
}

};