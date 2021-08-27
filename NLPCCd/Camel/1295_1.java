//,temp,sample_8374.java,2,17,temp,sample_8378.java,2,17
//,3
public class xxx {
public void dummy_method(){
ThreadPoolFactory threadPoolFactory = getBeanForType(ThreadPoolFactory.class);
if (threadPoolFactory != null) {
getContext().getExecutorServiceManager().setThreadPoolFactory(threadPoolFactory);
}
ProcessorFactory processorFactory = getBeanForType(ProcessorFactory.class);
if (processorFactory != null) {
getContext().setProcessorFactory(processorFactory);
}
Debugger debugger = getBeanForType(Debugger.class);
if (debugger != null) {


log.info("using custom debugger");
}
}

};