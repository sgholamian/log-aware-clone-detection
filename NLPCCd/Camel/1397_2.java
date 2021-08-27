//,temp,sample_8425.java,2,17,temp,sample_6767.java,2,18
//,3
public class xxx {
public void dummy_method(){
ModelMBeanInfo mbi = null;
if (obj instanceof ManagedInstance) {
Object custom = ((ManagedInstance) obj).getInstance();
if (custom != null && ObjectHelper.hasAnnotation(custom.getClass().getAnnotations(), ManagedResource.class)) {
mbi = springAssembler.getMBeanInfo(custom, name.toString());
obj = custom;
}
}
if (mbi == null) {
if (ObjectHelper.hasAnnotation(obj.getClass().getAnnotations(), ManagedResource.class)) {


log.info("assembling mbeaninfo for from managedresource object");
}
}
}

};