//,temp,sample_8336.java,2,17,temp,sample_8335.java,2,17
//,3
public class xxx {
public void dummy_method(){
setupCustomServices();
initCustomRegistry(getContext());
initPropertyPlaceholder();
initJMXAgent();
Tracer tracer = getBeanForType(Tracer.class);
if (tracer != null) {
TraceFormatter formatter = getBeanForType(TraceFormatter.class);
if (formatter != null) {
tracer.setFormatter(formatter);
}


log.info("using custom tracer");
}
}

};