//,temp,sample_912.java,2,14,temp,sample_923.java,2,16
//,3
public class xxx {
public void onEndpointRemove(Endpoint endpoint) {
if (!initialized) {
return;
}
try {
Object me = getManagementObjectStrategy().getManagedObjectForEndpoint(camelContext, endpoint);
unmanageObject(me);
} catch (Exception e) {


log.info("could not unregister endpoint mbean for endpoint this exception will be ignored");
}
}

};