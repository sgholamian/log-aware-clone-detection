//,temp,sample_3951.java,2,11,temp,sample_1046.java,2,14
//,3
public class xxx {
protected void doGetReplicationController(Exchange exchange, String operation) throws Exception {
ReplicationController rc = null;
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rcName)) {


log.info("get a specific replication controller require specify a replication controller name");
}
}

};