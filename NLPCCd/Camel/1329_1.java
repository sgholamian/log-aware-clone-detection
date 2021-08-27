//,temp,sample_3959.java,2,14,temp,sample_3955.java,2,17
//,3
public class xxx {
protected void doScaleReplicationController(Exchange exchange, String operation) throws Exception {
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
Integer replicasNumber = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_REPLICAS, Integer.class);
if (ObjectHelper.isEmpty(rcName)) {
throw new IllegalArgumentException( "Scale a specific replication controller require specify a replication controller name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("scale a specific replication controller require specify a namespace name");
}
}

};