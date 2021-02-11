//,temp,sample_7414.java,2,18,temp,sample_7405.java,2,18
//,3
public class xxx {
public void dummy_method(){
if (providersToDestroy.contains(element.getProvider())) {
try {
if (s_logger.isDebugEnabled()) {
}
if (!element.destroy(network, context)) {
success = false;
}
} catch (final ResourceUnavailableException e) {
success = false;
} catch (final ConcurrentOperationException e) {


log.info("unable to complete destroy of the network due to element");
}
}
}

};