//,temp,sample_2951.java,2,11,temp,sample_7112.java,2,15
//,3
public class xxx {
protected void handleDisableJmx(GenericApplicationContext context, Class<?> testClass) {
CamelSpringTestHelper.setOriginalJmxDisabledValue(System.getProperty(JmxSystemPropertyKeys.DISABLED));
if (testClass.isAnnotationPresent(DisableJmx.class)) {
if (testClass.getAnnotation(DisableJmx.class).value()) {


log.info("disabling camel jmx globally as disablejmx annotation was found and disablejmx is set to true");
}
}
}

};