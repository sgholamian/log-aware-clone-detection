//,temp,sample_7579.java,2,16,temp,sample_7580.java,2,17
//,3
public class xxx {
public void onExchangeDone(Route route, Exchange exchange) {
if (isExcluded(route.getId())) {
return;
}
LOG.trace("=> RoutePolicy-Done: Route: {} - RouteId: {}", routeId, route.getId());
SegmentDecorator sd = getSegmentDecorator(route.getEndpoint());
if (AWSXRay.getCurrentSubsegmentOptional().isPresent()) {
Subsegment subsegment = AWSXRay.getCurrentSubsegment();
sd.post(subsegment, exchange, route.getEndpoint());
subsegment.close();


log.info("closing down subsegment with name");
}
}

};