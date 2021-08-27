//,temp,KubernetesPodsProducer.java,133,157,temp,KubernetesPersistentVolumesClaimsProducer.java,141,169
//,2
public class xxx {
    protected void doCreatePod(Exchange exchange, String operation) throws Exception {
        Pod pod = null;
        String podName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_POD_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        PodSpec podSpec = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_POD_SPEC, PodSpec.class);
        if (ObjectHelper.isEmpty(podName)) {
            LOG.error("Create a specific pod require specify a pod name");
            throw new IllegalArgumentException("Create a specific pod require specify a pod name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific pod require specify a namespace name");
            throw new IllegalArgumentException("Create a specific pod require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(podSpec)) {
            LOG.error("Create a specific pod require specify a pod spec bean");
            throw new IllegalArgumentException("Create a specific pod require specify a pod spec bean");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PODS_LABELS, Map.class);
        Pod podCreating = new PodBuilder().withNewMetadata().withName(podName).withLabels(labels).endMetadata()
                .withSpec(podSpec).build();
        pod = getEndpoint().getKubernetesClient().pods().inNamespace(namespaceName).create(podCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pod);
    }

};