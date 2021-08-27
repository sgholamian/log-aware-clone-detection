//,temp,sample_7110.java,2,11,temp,sample_7120.java,2,12
//,3
public class xxx {
public static void handleDisableJmx(ConfigurableApplicationContext context, Class<?> testClass) {
CamelSpringTestHelper.setOriginalJmxDisabledValue(System.getProperty(JmxSystemPropertyKeys.DISABLED));
if (testClass.isAnnotationPresent(DisableJmx.class)) {
if (testClass.getAnnotation(DisableJmx.class).value()) {


log.info("disabling camel jmx globally as disablejmx annotation was found and disablejmx is set to true");
}
}
}

};