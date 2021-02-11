//,temp,sample_8796.java,2,12,temp,sample_8793.java,2,12
//,2
public class xxx {
private void createLoadBalancerHelperEvent(UsageEventVO event) {
long zoneId = -1L;
long id = event.getResourceId();
if (EventTypes.EVENT_LOAD_BALANCER_CREATE.equals(event.getType())) {
if (s_logger.isDebugEnabled()) {


log.info("creating load balancer for account");
}
}
}

};