//,temp,sample_3959.java,2,14,temp,sample_3955.java,2,17
//,3
public class xxx {
public void dummy_method(){
String rcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
ReplicationControllerSpec rcSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_REPLICATION_CONTROLLER_SPEC, ReplicationControllerSpec.class);
if (ObjectHelper.isEmpty(rcName)) {
throw new IllegalArgumentException( "Create a specific replication controller require specify a replication controller name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific replication controller require specify a namespace name");
}
if (ObjectHelper.isEmpty(rcSpec)) {


log.info("create a specific replication controller require specify a replication controller spec bean");
}
}

};