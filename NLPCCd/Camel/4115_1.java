//,temp,sample_6766.java,2,12,temp,sample_8424.java,2,14
//,3
public class xxx {
public ModelMBean assemble(MBeanServer mBeanServer, Object obj, ObjectName name) throws JMException {
ModelMBeanInfo mbi = null;
if (obj instanceof ManagedInstance) {
Object custom = ((ManagedInstance) obj).getInstance();
if (custom != null && ObjectHelper.hasAnnotation(custom.getClass().getAnnotations(), ManagedResource.class)) {


log.info("assembling mbeaninfo for from custom managedresource object");
}
}
}

};