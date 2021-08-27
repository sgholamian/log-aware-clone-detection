//,temp,sample_3958.java,2,11,temp,sample_827.java,2,17
//,3
public class xxx {
protected void doScaleReplicationController(Exchange exchange, String operation) throws Exception {
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
Integer replicasNumber = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_REPLICAS, Integer.class);
if (ObjectHelper.isEmpty(rcName)) {


log.info("scale a specific replication controller require specify a replication controller name");
}
}

};