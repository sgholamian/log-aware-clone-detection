//,temp,sample_5286.java,2,13,temp,sample_7302.java,2,13
//,2
public class xxx {
public void testRouteAddSecondRoute() throws Exception {
if (isPlatform("aix")) {
return;
}
MBeanServer mbeanServer = getMBeanServer();
ObjectName route1 = ObjectName.getInstance("org.apache.camel:context=camel-1,type=routes,name=\"foo\"");
String state = (String) mbeanServer.getAttribute(route1, "State");
assertEquals("Should be started", ServiceStatus.Started.name(), state);


log.info("adding route");
}

};