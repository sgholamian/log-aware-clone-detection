//,temp,sample_824.java,2,14,temp,sample_4325.java,2,12
//,3
public class xxx {
protected void doGetPersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
PersistentVolumeClaim pvc = null;
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(pvcName)) {
throw new IllegalArgumentException( "Get a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("get a specific persistent volume claim require specify a namespace name");
}
}

};