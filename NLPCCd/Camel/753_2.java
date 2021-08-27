//,temp,sample_2951.java,2,11,temp,sample_7112.java,2,15
//,3
public class xxx {
public static void handleDisableJmx(ConfigurableApplicationContext context, Class<?> testClass) {
CamelSpringTestHelper.setOriginalJmxDisabledValue(System.getProperty(JmxSystemPropertyKeys.DISABLED));
if (testClass.isAnnotationPresent(DisableJmx.class)) {
if (testClass.getAnnotation(DisableJmx.class).value()) {
System.setProperty(JmxSystemPropertyKeys.DISABLED, "true");
} else {
System.clearProperty(JmxSystemPropertyKeys.DISABLED);
}
} else {


log.info("disabling camel jmx globally for tests by default use the disablejmx annotation to override the default setting");
}
}

};