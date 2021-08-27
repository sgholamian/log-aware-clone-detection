//,temp,sample_8249.java,2,17,temp,sample_8244.java,2,17
//,3
public class xxx {
public void dummy_method(){
HandleFault handleFault = getSingleBeanOfType(applicationContext, HandleFault.class);
if (handleFault != null) {
camelContext.addInterceptStrategy(handleFault);
}
InflightRepository inflightRepository = getSingleBeanOfType(applicationContext, InflightRepository.class);
if (inflightRepository != null) {
camelContext.setInflightRepository(inflightRepository);
}
AsyncProcessorAwaitManager asyncProcessorAwaitManager = getSingleBeanOfType(applicationContext, AsyncProcessorAwaitManager.class);
if (asyncProcessorAwaitManager != null) {


log.info("using custom asyncprocessorawaitmanager");
}
}

};