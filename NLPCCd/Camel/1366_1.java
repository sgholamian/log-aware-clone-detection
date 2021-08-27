//,temp,sample_8340.java,2,17,temp,sample_8346.java,2,17
//,3
public class xxx {
public void dummy_method(){
org.apache.camel.processor.interceptor.Delayer delayer = getBeanForType(org.apache.camel.processor.interceptor.Delayer.class);
if (delayer != null) {
getContext().addInterceptStrategy(delayer);
}
InflightRepository inflightRepository = getBeanForType(InflightRepository.class);
if (inflightRepository != null) {
getContext().setInflightRepository(inflightRepository);
}
AsyncProcessorAwaitManager asyncProcessorAwaitManager = getBeanForType(AsyncProcessorAwaitManager.class);
if (asyncProcessorAwaitManager != null) {


log.info("using custom asyncprocessorawaitmanager");
}
}

};