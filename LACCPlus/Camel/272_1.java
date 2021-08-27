//,temp,KubernetesPersistentVolumesClaimsProducer.java,122,139,temp,KubernetesServicesProducer.java,123,138
//,2
public class xxx {
    protected void doGetPersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
        PersistentVolumeClaim pvc = null;
        String pvcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(pvcName)) {
            LOG.error("Get a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
            throw new IllegalArgumentException(
                    "Get a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific Persistent Volume Claim require specify a namespace name");
            throw new IllegalArgumentException("Get a specific Persistent Volume Claim require specify a namespace name");
        }
        pvc = getEndpoint().getKubernetesClient().persistentVolumeClaims().inNamespace(namespaceName).withName(pvcName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pvc);
    }

};