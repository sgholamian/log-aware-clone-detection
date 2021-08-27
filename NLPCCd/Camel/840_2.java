//,temp,sample_823.java,2,11,temp,sample_3956.java,2,10
//,3
public class xxx {
protected void doDeleteReplicationController(Exchange exchange, String operation) throws Exception {
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(rcName)) {


log.info("delete a specific replication controller require specify a replication controller name");
}
}

};