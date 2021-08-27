//,temp,KubernetesPodsProducer.java,133,157,temp,KubernetesPersistentVolumesClaimsProducer.java,141,169
//,2
public class xxx {
    protected void doCreatePersistentVolumeClaim(Exchange exchange, String operation) throws Exception {
        PersistentVolumeClaim pvc = null;
        String pvcName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        PersistentVolumeClaimSpec pvcSpec = exchange.getIn()
                .getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUME_CLAIM_SPEC, PersistentVolumeClaimSpec.class);
        if (ObjectHelper.isEmpty(pvcName)) {
            LOG.error("Create a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
            throw new IllegalArgumentException(
                    "Create a specific Persistent Volume Claim require specify a Persistent Volume Claim name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific Persistent Volume Claim require specify a namespace name");
            throw new IllegalArgumentException("Create a specific Persistent Volume Claim require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(pvcSpec)) {
            LOG.error("Create a specific Persistent Volume Claim require specify a Persistent Volume Claim spec bean");
            throw new IllegalArgumentException(
                    "Create a specific Persistent Volume Claim require specify a Persistent Volume Claim spec bean");
        }
        Map<String, String> labels
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PERSISTENT_VOLUMES_CLAIMS_LABELS, Map.class);
        PersistentVolumeClaim pvcCreating = new PersistentVolumeClaimBuilder().withNewMetadata().withName(pvcName)
                .withLabels(labels).endMetadata().withSpec(pvcSpec).build();
        pvc = getEndpoint().getKubernetesClient().persistentVolumeClaims().inNamespace(namespaceName).create(pvcCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pvc);
    }

};