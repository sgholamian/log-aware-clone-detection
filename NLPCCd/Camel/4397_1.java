//,temp,sample_825.java,2,12,temp,sample_4326.java,2,15
//,3
public class xxx {
protected void doCreatePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
PersistentVolumeClaim pvc = null;
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
PersistentVolumeClaimSpec pvcSpec = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_SPEC, PersistentVolumeClaimSpec.class);
if (ObjectHelper.isEmpty(pvcName)) {


log.info("create a specific persistent volume claim require specify a persistent volume claim name");
}
}

};