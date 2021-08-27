//,temp,sample_2538.java,2,16,temp,sample_2537.java,2,15
//,2
public class xxx {
protected Message getFallback() {
synchronized (lock) {
fallbackInUse.set(true);
}
if (fallback == null && fallbackCommand == null) {
throw new UnsupportedOperationException("No fallback available.");
}
Throwable exception = getExecutionException();
if (exception != null) {
} else {


log.info("error occurred processing will now run fallback");
}
}

};