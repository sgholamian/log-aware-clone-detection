//,temp,sample_8339.java,2,17,temp,sample_8377.java,2,17
//,3
public class xxx {
public void dummy_method(){
HandleFault handleFault = getBeanForType(HandleFault.class);
if (handleFault != null) {
getContext().addInterceptStrategy(handleFault);
}
org.apache.camel.processor.interceptor.Delayer delayer = getBeanForType(org.apache.camel.processor.interceptor.Delayer.class);
if (delayer != null) {
getContext().addInterceptStrategy(delayer);
}
InflightRepository inflightRepository = getBeanForType(InflightRepository.class);
if (inflightRepository != null) {


log.info("using custom inflightrepository");
}
}

};