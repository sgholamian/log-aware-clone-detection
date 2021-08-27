//,temp,sample_6098.java,2,17,temp,sample_914.java,2,17
//,3
public class xxx {
public void dummy_method(){
Object managedObject = camelContext.getManagementStrategy().getManagementObjectStrategy().getManagedObjectForService(camelContext, reload);
if (managedObject == null) {
return;
}
if (camelContext.getManagementStrategy().isManaged(managedObject, null)) {
return;
}
try {
camelContext.getManagementStrategy().manageObject(managedObject);
} catch (Exception e) {


log.info("could not register service as service mbean");
}
}

};