//,temp,sample_3324.java,2,18,temp,sample_3323.java,2,16
//,3
public class xxx {
protected void doStart() throws Exception {
ObjectHelper.notNull(camelContext, "CamelContext", this);
camelContext.getManagementStrategy().addEventNotifier(eventNotifier);
if (!camelContext.getRoutePolicyFactories().contains(this)) {
camelContext.addRoutePolicyFactory(this);
}
if (spanReporter == null) {
if (spanCollector != null) {
} else if (endpoint != null) {


log.info("configuring zipkin urlconnectionsender using endpoint");
}
}
}

};