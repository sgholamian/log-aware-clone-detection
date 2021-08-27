//,temp,sample_7579.java,2,16,temp,sample_7580.java,2,17
//,3
public class xxx {
public void dummy_method(){
LOG.trace("=> RoutePolicy-Done: Route: {} - RouteId: {}", routeId, route.getId());
SegmentDecorator sd = getSegmentDecorator(route.getEndpoint());
if (AWSXRay.getCurrentSubsegmentOptional().isPresent()) {
Subsegment subsegment = AWSXRay.getCurrentSubsegment();
sd.post(subsegment, exchange, route.getEndpoint());
subsegment.close();
} else if (AWSXRay.getCurrentSegmentOptional().isPresent()) {
Segment segment = AWSXRay.getCurrentSegment();
sd.post(segment, exchange, route.getEndpoint());
segment.close();


log.info("closing down segment with name");
}
}

};