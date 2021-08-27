//,temp,sample_4164.java,2,11,temp,sample_4163.java,2,9
//,3
public class xxx {
private void stopRouteImpl(Route route) throws Exception {
ServiceStatus routeStatus = route.getRouteContext().getCamelContext().getRouteStatus(route.getId());
if (routeStatus == ServiceStatus.Started) {


log.info("stopping");
}
}

};