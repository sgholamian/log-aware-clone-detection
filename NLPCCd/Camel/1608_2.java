//,temp,sample_4878.java,2,13,temp,sample_3954.java,2,15
//,3
public class xxx {
protected void doCreateReplicationController(Exchange exchange, String operation) throws Exception {
ReplicationController rc = null;
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ReplicationControllerSpec rcSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_SPEC, ReplicationControllerSpec.class);
if (ObjectHelper.isEmpty(rcName)) {
throw new IllegalArgumentException( "Create a specific replication controller require specify a replication controller name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific replication controller require specify a namespace name");
}
}

};