//,temp,sample_7590.java,2,12,temp,sample_7592.java,2,16
//,3
public class xxx {
protected void serviceStop() throws Exception {
stopExecutors.set(true);
if (executor != null) {
executor.shutdown();
if (executor.isTerminating()) {
boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);
if (terminated) {
} else {


log.info("executor did not terminate");
}
}
}
}

};