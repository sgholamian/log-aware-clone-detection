//,temp,sample_8243.java,2,17,temp,sample_8247.java,2,17
//,3
public class xxx {
public void dummy_method(){
BacklogTracer backlogTracer = getSingleBeanOfType(applicationContext, BacklogTracer.class);
if (backlogTracer != null) {
camelContext.addInterceptStrategy(backlogTracer);
}
HandleFault handleFault = getSingleBeanOfType(applicationContext, HandleFault.class);
if (handleFault != null) {
camelContext.addInterceptStrategy(handleFault);
}
InflightRepository inflightRepository = getSingleBeanOfType(applicationContext, InflightRepository.class);
if (inflightRepository != null) {


log.info("using custom inflightrepository");
}
}

};