//,temp,sample_3324.java,2,18,temp,sample_3323.java,2,16
//,3
public class xxx {
public void dummy_method(){
ObjectHelper.notNull(camelContext, "CamelContext", this);
camelContext.getManagementStrategy().addEventNotifier(eventNotifier);
if (!camelContext.getRoutePolicyFactories().contains(this)) {
camelContext.addRoutePolicyFactory(this);
}
if (spanReporter == null) {
if (spanCollector != null) {
} else if (endpoint != null) {
spanReporter = AsyncReporter.create(URLConnectionSender.create(endpoint));
} else if (hostName != null && port > 0) {


log.info("configuring zipkin scribespancollector using host and port");
}
}
}

};