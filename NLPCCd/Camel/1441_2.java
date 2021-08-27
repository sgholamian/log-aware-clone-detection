//,temp,sample_7153.java,2,16,temp,sample_7159.java,2,14
//,3
public class xxx {
public void testRouteAddRemoteRouteWithRecipientListAndRouteScopedOnException() throws Exception {
MockEndpoint result = getMockEndpoint("mock:result");
result.expectedMessageCount(1);
template.sendBody("direct:start", "Hello World");
result.assertIsSatisfied();
MBeanServer mbeanServer = getMBeanServer();
ObjectName on = ObjectName.getInstance("org.apache.camel:context=camel-1,type=services,*");
Set<ObjectName> names = mbeanServer.queryNames(on, null);
assertEquals(services, names.size());


log.info("adding route");
}

};