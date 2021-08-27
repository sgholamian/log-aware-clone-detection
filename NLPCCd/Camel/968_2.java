//,temp,sample_8372.java,2,17,temp,sample_8373.java,2,17
//,3
public class xxx {
public void dummy_method(){
ExecutorServiceManager executorServiceStrategy = getBeanForType(ExecutorServiceManager.class);
if (executorServiceStrategy != null) {
getContext().setExecutorServiceManager(executorServiceStrategy);
}
ThreadPoolFactory threadPoolFactory = getBeanForType(ThreadPoolFactory.class);
if (threadPoolFactory != null) {
getContext().getExecutorServiceManager().setThreadPoolFactory(threadPoolFactory);
}
ProcessorFactory processorFactory = getBeanForType(ProcessorFactory.class);
if (processorFactory != null) {


log.info("using custom processorfactory");
}
}

};