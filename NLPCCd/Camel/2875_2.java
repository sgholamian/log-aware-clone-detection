//,temp,sample_1970.java,2,11,temp,sample_1971.java,2,15
//,3
public class xxx {
protected boolean isRedeliveryAllowed(RedeliveryData data) {
if (data.redeliveryCounter > 0) {
boolean stopping = isStoppingOrStopped();
if (!preparingShutdown && !stopping) {
return true;
} else {
if (data.currentRedeliveryPolicy.allowRedeliveryWhileStopping) {


log.info("isredeliveryallowed true redelivery allowed as redeliverwhilestopping is enabled");
}
}
}
}

};