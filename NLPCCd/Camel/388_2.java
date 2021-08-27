//,temp,sample_5795.java,2,11,temp,sample_2193.java,2,13
//,3
public class xxx {
private void resizeThreadPoolExecutor(final int newSize) {
if (executor == null && newSize > 0) {
executor = component.getCamelContext().getExecutorServiceManager().newFixedThreadPool(this, uri, newSize);
} else if (executor != null && newSize <= 0) {
component.getCamelContext().getExecutorServiceManager().shutdown(executor);
executor = null;
} else if (executor instanceof ThreadPoolExecutor) {


log.info("resizing existing executor to threads");
}
}

};