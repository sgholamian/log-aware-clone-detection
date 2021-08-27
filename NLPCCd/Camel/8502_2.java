//,temp,sample_5986.java,2,17,temp,sample_5987.java,2,17
//,2
public class xxx {
public void dummy_method(){
StopWatch watch = new StopWatch();
doStartOrResumeRoutes(suspendedRouteServices, false, true, true, false);
for (RouteService service : suspendedRouteServices.values()) {
if (routeSupportsSuspension(service.getId())) {
service.resume();
} else {
service.start();
}
}
if (log.isInfoEnabled()) {


log.info("apache camel camelcontext resumed in");
}
}

};