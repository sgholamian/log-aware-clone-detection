//,temp,sample_2245.java,2,14,temp,sample_2247.java,2,16
//,3
public class xxx {
private AtomixReplica getOrCreateReplica() throws Exception {
if (atomix == null) {
ObjectHelper.notNull(getCamelContext(), "Camel Context");
ObjectHelper.notNull(address, "Atomix Node Address");
ObjectHelper.notNull(configuration, "Atomix Node Configuration");
atomix = AtomixClusterHelper.createReplica(getCamelContext(), address, configuration);
if (ObjectHelper.isNotEmpty(configuration.getNodes())) {


log.info("bootstrap cluster on address for nodes");
}
}
}

};