//,temp,sample_3187.java,2,15,temp,sample_3186.java,2,14
//,3
public class xxx {
private AtomixClient getOrCreateClient() throws Exception {
if (atomix == null) {
ObjectHelper.notNull(getCamelContext(), "Camel Context");
ObjectHelper.notNull(configuration, "Atomix Node Configuration");
if (ObjectHelper.isEmpty(configuration.getNodes())) {
throw new IllegalArgumentException("Atomix nodes should not be empty");
}
atomix = AtomixClientHelper.createClient(getCamelContext(), configuration);
atomix.connect(configuration.getNodes()).join();


log.info("connect to cluster done");
}
}

};