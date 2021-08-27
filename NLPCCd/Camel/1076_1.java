//,temp,sample_2206.java,2,16,temp,sample_7171.java,2,14
//,3
public class xxx {
public void dummy_method(){
result.expectedMessageCount(1);
template.sendBody("direct:start", "Hello World");
result.assertIsSatisfied();
MBeanServer mbeanServer = getMBeanServer();
ObjectName on = ObjectName.getInstance("org.apache.camel:context=camel-1,type=services,*");
Set<ObjectName> names = mbeanServer.queryNames(on, null);
assertEquals(services, names.size());
ObjectName onP = ObjectName.getInstance("org.apache.camel:context=camel-1,type=producers,*");
Set<ObjectName> namesP = mbeanServer.queryNames(onP, null);
assertEquals(3, namesP.size());


log.info("adding route");
}

};