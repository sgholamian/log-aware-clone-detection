//,temp,sample_906.java,2,16,temp,sample_922.java,2,17
//,3
public class xxx {
public void onContextStop(CamelContext context) {
if (!initialized) {
return;
}
try {
Object mc = getManagementObjectStrategy().getManagedObjectForRouteController(context);
if (getManagementStrategy().isManaged(mc, null)) {
unmanageObject(mc);
}
} catch (Exception e) {


log.info("could not unregister routecontroller mbean");
}
}

};