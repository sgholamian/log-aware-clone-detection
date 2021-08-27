//,temp,sample_1967.java,2,14,temp,sample_1972.java,2,17
//,3
public class xxx {
protected boolean isRunAllowed(RedeliveryData data) {
boolean forceShutdown = camelContext.getShutdownStrategy().forceShutdown(this);
if (forceShutdown) {
return false;
}
if (data.redeliveryCounter > 0) {
if (data.currentRedeliveryPolicy.allowRedeliveryWhileStopping) {


log.info("isrunallowed true run allowed as redeliverwhilestopping is enabled");
}
}
}

};