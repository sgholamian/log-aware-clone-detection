//,temp,sample_8344.java,2,17,temp,sample_8338.java,2,17
//,3
public class xxx {
public void dummy_method(){
BacklogTracer backlogTracer = getBeanForType(BacklogTracer.class);
if (backlogTracer != null) {
getContext().addInterceptStrategy(backlogTracer);
}
HandleFault handleFault = getBeanForType(HandleFault.class);
if (handleFault != null) {
getContext().addInterceptStrategy(handleFault);
}
org.apache.camel.processor.interceptor.Delayer delayer = getBeanForType(org.apache.camel.processor.interceptor.Delayer.class);
if (delayer != null) {


log.info("using custom delayer");
}
}

};