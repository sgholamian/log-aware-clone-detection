//,temp,sample_3960.java,2,17,temp,sample_580.java,2,17
//,2
public class xxx {
public void dummy_method(){
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
Integer replicasNumber = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_REPLICAS, Integer.class);
if (ObjectHelper.isEmpty(rcName)) {
throw new IllegalArgumentException( "Scale a specific replication controller require specify a replication controller name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Scale a specific replication controller require specify a namespace name");
}
if (ObjectHelper.isEmpty(replicasNumber)) {


log.info("scale a specific replication controller require specify a replicas number");
}
}

};