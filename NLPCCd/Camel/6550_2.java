//,temp,sample_5829.java,2,8,temp,sample_5830.java,2,11
//,3
public class xxx {
private int getMbeanCount(final String name) throws MalformedObjectNameException {
MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
Set<ObjectInstance> mbeans = mbs.queryMBeans(new ObjectName("org.apache.camel:type=threadpools,*"), null);
int count = 0;
for (ObjectInstance mbean : mbeans) {


log.info("mbean");
}
}

};