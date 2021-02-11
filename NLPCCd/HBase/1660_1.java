//,temp,sample_659.java,2,19,temp,sample_660.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (LOG.isTraceEnabled()) {
}
Collection<MetricRegistry> registries = MetricRegistries.global().getMetricRegistries();
for (MetricRegistry registry : registries) {
MetricRegistryInfo info = registry.getMetricRegistryInfo();
if (info.isExistingSource()) {
continue;
}
if (!registeredSources.containsKey(info)) {
if (LOG.isDebugEnabled()) {


log.info("registering adapter for the metricregistry");
}
}
}
}

};