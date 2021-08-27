//,temp,sample_1364.java,2,19,temp,sample_1360.java,2,19
//,3
public class xxx {
public void dummy_method(){
long seconds = watch.taken() / 1000;
boolean result = seconds >= maxIdleSeconds;
LOG.trace("Duration max idle check {} >= {} -> {}", seconds, maxIdleSeconds, result);
if (result) {
if (completed.compareAndSet(false, true)) {
try {
if (stopCamelContext) {
camelContext.stop();
}
} catch (Exception e) {


log.info("error during stopping camelcontext this exception is ignored");
}
}
}
}

};