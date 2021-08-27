//,temp,sample_4161.java,2,11,temp,sample_4160.java,2,9
//,3
public class xxx {
private void startRouteImpl(Route route) throws Exception {
ServiceStatus routeStatus = route.getRouteContext().getCamelContext().getRouteStatus(route.getId());
if (routeStatus == ServiceStatus.Stopped) {
startRoute(route);
} else if (routeStatus == ServiceStatus.Suspended) {


log.info("resuming");
}
}

};