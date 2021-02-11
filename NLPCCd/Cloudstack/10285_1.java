//,temp,sample_8315.java,2,17,temp,sample_8314.java,2,17
//,3
public class xxx {
public void dummy_method(){
lifecycle.start();
if (lifecycle instanceof ManagementBean) {
ManagementBean mbean = (ManagementBean)lifecycle;
try {
JmxUtil.registerMBean(mbean);
} catch (MalformedObjectNameException e) {
} catch (InstanceAlreadyExistsException e) {
} catch (MBeanRegistrationException e) {
} catch (NotCompliantMBeanException e) {
}


log.info("registered mbean");
}
}

};