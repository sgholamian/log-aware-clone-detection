//,temp,sample_8241.java,2,17,temp,sample_8245.java,2,17
//,3
public class xxx {
public void dummy_method(){
Tracer tracer = getSingleBeanOfType(applicationContext, Tracer.class);
if (tracer != null) {
TraceFormatter formatter = getSingleBeanOfType(applicationContext, TraceFormatter.class);
if (formatter != null) {
tracer.setFormatter(formatter);
}
camelContext.addInterceptStrategy(tracer);
}
BacklogTracer backlogTracer = getSingleBeanOfType(applicationContext, BacklogTracer.class);
if (backlogTracer != null) {


log.info("using custom backlogtracer");
}
}

};