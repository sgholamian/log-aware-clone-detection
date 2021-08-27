//,temp,sample_4361.java,2,13,temp,sample_4495.java,2,10
//,3
public class xxx {
protected void doStop() throws Exception {
super.doStop();
if (scheduledExecutor != null) {
getEndpoint().getCamelContext().getExecutorServiceManager().shutdownNow(scheduledExecutor);
scheduledExecutor = null;
}
if (muc != null) {


log.info("leaving room");
}
}

};