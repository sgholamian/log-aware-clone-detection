//,temp,sample_6396.java,2,13,temp,sample_6395.java,2,11
//,3
public class xxx {
private void registerMBeanWithServer(Object obj, ObjectName name, boolean forceRegistration) throws JMException {
boolean exists = isRegistered(name);
if (exists) {
if (forceRegistration) {


log.info("forceregistration enabled unregistering existing mbean with objectname");
}
}
}

};