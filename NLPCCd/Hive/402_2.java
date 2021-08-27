//,temp,sample_3496.java,2,17,temp,sample_3495.java,2,15
//,3
public class xxx {
public void stop() {
if (!(this.isStopping() || this.isStopped())) {
super.stop();
if (appenderControl.get() != null) {
appenderControl.get().stop();
realAppender.get().stop();
}
if (LOGGER.isDebugEnabled()) {


log.info("stop invoked for");
}
}
}

};