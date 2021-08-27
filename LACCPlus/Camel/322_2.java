//,temp,KubernetesPodsProducer.java,159,174,temp,KubernetesPersistentVolumesClaimsProducer.java,171,188
//,2
public class xxx {
    protected void doDeletePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
        String pvcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(pvcName)) {
            LOG.error("Delete a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
            throw new IllegalArgumentException(
                    "Delete a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific Persistent Volume Claim require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific Persistent Volume Claim require specify a namespace name");
        }
        boolean pvcDeleted = getEndpoint().getKubernetesClient().persistentVolumeClaims().inNamespace(namespaceName)
                .withName(pvcName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pvcDeleted);
    }

};