//,temp,sample_1000.java,2,10,temp,sample_8029.java,2,10
//,2
public class xxx {
private void onCamelContextStarted() {
if (contextStarted.compareAndSet(false, true)) {
if (initialDelay.toMillis() > 0) {


log.info("routes will be started in");
}
}
}

};