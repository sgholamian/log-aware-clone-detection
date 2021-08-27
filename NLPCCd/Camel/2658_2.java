//,temp,sample_8201.java,2,18,temp,sample_5774.java,2,18
//,2
public class xxx {
public void dummy_method(){
if (action == Action.START) {
if (routeStatus == ServiceStatus.Stopped) {
startRoute(route);
} else if (ServiceHelper.isSuspended(route.getConsumer())) {
resumeOrStartConsumer(route.getConsumer());
}
} else if (action == Action.STOP) {
if ((routeStatus == ServiceStatus.Started) || (routeStatus == ServiceStatus.Suspended)) {
stopRoute(route, getRouteStopGracePeriod(), getTimeUnit());
} else {


log.info("route is not in a started suspended state and cannot be stopped the current route state is");
}
}
}

};