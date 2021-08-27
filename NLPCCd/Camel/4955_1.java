//,temp,sample_7716.java,2,13,temp,sample_7717.java,2,18
//,3
public class xxx {
public void onCamelContextStarted(CamelContext context, boolean alreadyStarted) throws Exception {
if (scheduler == null) {
createAndInitScheduler();
} else {
storeCamelContextInQuartzContext();
}
if (!autoStartScheduler) {


log.info("not starting scheduler because autostartscheduler is set to false");
}
}

};