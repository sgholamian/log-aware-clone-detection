//,temp,sample_5640.java,2,13,temp,sample_3952.java,2,14
//,3
public class xxx {
protected void doGetReplicationController(Exchange exchange, String operation) throws Exception {
ReplicationController rc = null;
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rcName)) {
throw new IllegalArgumentException( "Get a specific replication controller require specify a replication controller name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific replication controller require specify a namespace name");
}
}

};