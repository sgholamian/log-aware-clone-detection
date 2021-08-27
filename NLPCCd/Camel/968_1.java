//,temp,sample_8372.java,2,17,temp,sample_8373.java,2,17
//,3
public class xxx {
public void dummy_method(){
FactoryFinderResolver factoryFinderResolver = getBeanForType(FactoryFinderResolver.class);
if (factoryFinderResolver != null) {
getContext().setFactoryFinderResolver(factoryFinderResolver);
}
ExecutorServiceManager executorServiceStrategy = getBeanForType(ExecutorServiceManager.class);
if (executorServiceStrategy != null) {
getContext().setExecutorServiceManager(executorServiceStrategy);
}
ThreadPoolFactory threadPoolFactory = getBeanForType(ThreadPoolFactory.class);
if (threadPoolFactory != null) {


log.info("using custom threadpoolfactory");
}
}

};