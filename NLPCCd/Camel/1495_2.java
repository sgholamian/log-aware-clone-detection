//,temp,sample_3958.java,2,11,temp,sample_827.java,2,17
//,3
public class xxx {
public void dummy_method(){
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PersistentVolumeClaimSpec pvcSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_SPEC, PersistentVolumeClaimSpec.class);
if (ObjectHelper.isEmpty(pvcName)) {
throw new IllegalArgumentException( "Create a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
}
if (ObjectHelper.isEmpty(namespaceName)) {
throw new IllegalArgumentException( "Create a specific Persistent Volume Claim require specify a namespace name");
}
if (ObjectHelper.isEmpty(pvcSpec)) {


log.info("create a specific persistent volume claim require specify a persistent volume claim spec bean");
}
}

};