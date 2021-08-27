//,temp,sample_1967.java,2,14,temp,sample_1972.java,2,17
//,3
public class xxx {
protected boolean isRedeliveryAllowed(RedeliveryData data) {
if (data.redeliveryCounter > 0) {
boolean stopping = isStoppingOrStopped();
if (!preparingShutdown && !stopping) {
return true;
} else {
if (data.currentRedeliveryPolicy.allowRedeliveryWhileStopping) {
return true;
} else {


log.info("isredeliveryallowed false redelivery not allowed as redeliverwhilestopping is disabled");
}
}
}
}

};