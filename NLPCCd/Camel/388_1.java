//,temp,sample_5795.java,2,11,temp,sample_2193.java,2,13
//,3
public class xxx {
private void setupTasks() {
int poolSize = endpoint.getConcurrentConsumers();
if (executor == null) {
executor = endpoint.getCamelContext().getExecutorServiceManager().newFixedThreadPool(this, endpoint.getEndpointUri(), poolSize);
}
int tasks = poolSize - taskCount.get();


log.info("creating consumer tasks with poll timeout ms");
}

};