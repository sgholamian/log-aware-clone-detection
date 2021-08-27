//,temp,sample_926.java,2,14,temp,sample_919.java,2,14
//,3
public class xxx {
public void onRoutesRemove(Collection<Route> routes) {
if (!initialized) {
return;
}
for (Route route : routes) {
Object mr = getManagementObjectStrategy().getManagedObjectForRoute(camelContext, route);
if (!getManagementStrategy().isManaged(mr, null)) {


log.info("the route is not managed");
}
}
}

};