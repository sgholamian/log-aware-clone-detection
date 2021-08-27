//,temp,sample_4536.java,2,9,temp,sample_4535.java,2,9
//,3
public class xxx {
private List<String> assertCompletion(MBeanServer mbeanServer, ObjectName on, String componentName, Map<String, Object> properties, String completionText) throws Exception {
Object[] params = {componentName, properties, completionText};
String[] signature = {"java.lang.String",  "java.util.Map",  "java.lang.String"};
List<?> completions = assertIsInstanceOf(List.class, mbeanServer.invoke(on, "completeEndpointPath", params, signature));


log.info("component with returned");
}

};