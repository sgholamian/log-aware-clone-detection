//,temp,sample_5991.java,2,17,temp,sample_5990.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (log.isInfoEnabled()) {
int started = 0;
for (Route route : getRoutes()) {
ServiceStatus status = getRouteStatus(route.getId());
if (status != null && status.isStarted()) {
started++;
}
}
final Collection<Route> controlledRoutes = getRouteController().getControlledRoutes();
if (controlledRoutes.isEmpty()) {


log.info("total routes of which are started");
}
}
}

};