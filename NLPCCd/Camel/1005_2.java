//,temp,sample_4661.java,2,18,temp,sample_4660.java,2,16
//,3
public class xxx {
private void doRun() {
if (isSuspended()) {
return;
}
if (backoffMultiplier > 0 && (idleCounter >= (backoffIdleThreshold > 0 ? backoffIdleThreshold : Integer.MAX_VALUE)) || errorCounter >= (backoffErrorThreshold > 0 ? backoffErrorThreshold : Integer.MAX_VALUE)) {
if (backoffCounter++ < backoffMultiplier) {
if (idleCounter > 0) {
} else {


log.info("dorun backoff due subsequent errors backoff at");
}
}
}
}

};