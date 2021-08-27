//,temp,sample_8425.java,2,17,temp,sample_6767.java,2,18
//,3
public class xxx {
public void dummy_method(){
ModelMBeanInfo standardMbi = null;
Object custom = null;
if (obj instanceof ManagedInstance) {
custom = ((ManagedInstance) obj).getInstance();
if (custom != null && ObjectHelper.hasAnnotation(custom.getClass().getAnnotations(), ManagedResource.class)) {
mbi = assembler.getMBeanInfo(obj, custom, name.toString());
standardMbi = assembler.getMBeanInfo(obj, null, name.toString());
}
}
if (mbi == null) {


log.info("assembling mbeaninfo for from managedresource object");
}
}

};