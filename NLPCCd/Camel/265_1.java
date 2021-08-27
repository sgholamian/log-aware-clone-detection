//,temp,sample_4536.java,2,9,temp,sample_4535.java,2,9
//,3
public class xxx {
private String assertParameterJsonSchema(MBeanServer mbeanServer, ObjectName on, String componentName) throws Exception {
Object[] params = {componentName};
String[] signature = {"java.lang.String"};
String answer = assertIsInstanceOf(String.class, mbeanServer.invoke(on, "componentParameterJsonSchema", params, signature));


log.info("component returned json");
}

};