//,temp,sample_7566.java,2,15,temp,sample_7565.java,2,13
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(camelContext, "CamelContext", this);
camelContext.getManagementStrategy().addEventNotifier(eventNotifier);
if (!camelContext.getRoutePolicyFactories().contains(this)) {
camelContext.addRoutePolicyFactory(this);
}
if (null == tracingStrategy) {


log.info("no tracing strategy available defaulting to no op strategy");
}
}

};