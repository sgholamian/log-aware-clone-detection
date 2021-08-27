//,temp,sample_2610.java,2,17,temp,sample_3327.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (spanReporter == null && spanCollector == null) {
Set<Reporter> reporters = camelContext.getRegistry().findByType(Reporter.class);
if (reporters.size() == 1) {
spanReporter = reporters.iterator().next();
}
}
if (spanCollector == null) {
ObjectHelper.notNull(spanReporter, "Reporter<zipkin2.Span>", this);
}
if (clientServiceMappings.isEmpty() && serverServiceMappings.isEmpty()) {


log.info("no service name s has been mapped in clientservicemappings or serverservicemappings camel will fallback and use endpoint uris as service names");
}
}

};