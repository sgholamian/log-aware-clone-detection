//,temp,sample_5635.java,2,11,temp,sample_828.java,2,10
//,3
public class xxx {
protected void doDeletePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
String pvcName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
String namespaceName = exchange.getIn().getHeader( KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
if (ObjectHelper.isEmpty(pvcName)) {


log.info("delete a specific persistent volume claim require specify a persistent volume claim name");
}
}

};