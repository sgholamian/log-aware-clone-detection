//,temp,sample_2248.java,2,17,temp,sample_2246.java,2,15
//,3
public class xxx {
private AtomixReplica getOrCreateReplica() throws Exception {
if (atomix == null) {
ObjectHelper.notNull(getCamelContext(), "Camel Context");
ObjectHelper.notNull(address, "Atomix Node Address");
ObjectHelper.notNull(configuration, "Atomix Node Configuration");
atomix = AtomixClusterHelper.createReplica(getCamelContext(), address, configuration);
if (ObjectHelper.isNotEmpty(configuration.getNodes())) {
this.atomix.bootstrap(configuration.getNodes()).join();


log.info("bootstrap cluster done");
}
}
}

};