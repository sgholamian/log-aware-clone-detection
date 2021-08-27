//,temp,KubernetesPodsProducer.java,159,174,temp,KubernetesPersistentVolumesClaimsProducer.java,171,188
//,2
public class xxx {
    protected void doDeletePod(Exchange exchange, String operation) throws Exception {
        String podName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_POD_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(podName)) {
            LOG.error("Delete a specific pod require specify a pod name");
            throw new IllegalArgumentException("Delete a specific pod require specify a pod name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific pod require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific pod require specify a namespace name");
        }
        boolean podDeleted = getEndpoint().getKubernetesClient().pods().inNamespace(namespaceName).withName(podName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(podDeleted);
    }

};