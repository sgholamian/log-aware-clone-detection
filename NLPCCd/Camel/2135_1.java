//,temp,sample_8371.java,2,17,temp,sample_8342.java,2,17
//,2
public class xxx {
public void dummy_method(){
ClassResolver classResolver = getBeanForType(ClassResolver.class);
if (classResolver != null) {
getContext().setClassResolver(classResolver);
}
FactoryFinderResolver factoryFinderResolver = getBeanForType(FactoryFinderResolver.class);
if (factoryFinderResolver != null) {
getContext().setFactoryFinderResolver(factoryFinderResolver);
}
ExecutorServiceManager executorServiceStrategy = getBeanForType(ExecutorServiceManager.class);
if (executorServiceStrategy != null) {


log.info("using custom executorservicestrategy");
}
}

};