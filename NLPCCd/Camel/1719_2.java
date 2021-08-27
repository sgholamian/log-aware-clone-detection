//,temp,sample_576.java,2,11,temp,sample_829.java,2,13
//,3
public class xxx {
protected void doDeletePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(pvcName)) {
throw new IllegalArgumentException( "Delete a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
}
if (ObjectHelper.isEmpty(namespaceName)) {


log.info("delete a specific persistent volume claim require specify a namespace name");
}
}

};