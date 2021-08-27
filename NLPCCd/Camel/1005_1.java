//,temp,sample_4661.java,2,18,temp,sample_4660.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (backoffMultiplier > 0 && (idleCounter >= (backoffIdleThreshold > 0 ? backoffIdleThreshold : Integer.MAX_VALUE)) || errorCounter >= (backoffErrorThreshold > 0 ? backoffErrorThreshold : Integer.MAX_VALUE)) {
if (backoffCounter++ < backoffMultiplier) {
if (idleCounter > 0) {
} else {
}
return;
} else {
idleCounter = 0;
errorCounter = 0;
backoffCounter = 0;


log.info("dorun backoff finished resetting counters");
}
}
}

};