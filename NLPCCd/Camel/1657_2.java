//,temp,sample_1364.java,2,19,temp,sample_1360.java,2,19
//,3
public class xxx {
public void dummy_method(){
doneMessages++;
boolean result = doneMessages >= maxMessages;
LOG.trace("Duration max messages check {} >= {} -> {}", doneMessages, maxMessages, result);
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