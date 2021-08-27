//,temp,sample_582.java,2,13,temp,sample_826.java,2,15
//,3
public class xxx {
protected void doCreatePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
PersistentVolumeClaim pvc = null;
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PersistentVolumeClaimSpec pvcSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_SPEC, PersistentVolumeClaimSpec.class);
if (ObjectHelper.isEmpty(pvcName)) {
throw new IllegalArgumentException( "Create a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("create a specific persistent volume claim require specify a namespace name");
}
}

};