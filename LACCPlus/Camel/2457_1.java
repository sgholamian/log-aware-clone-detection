//,temp,KubernetesPodsProducer.java,115,131,temp,KubernetesDeploymentsProducer.java,112,123
//,3
public class xxx {
    protected void doGetPod(Exchange exchange, String operation) throws Exception {
        Pod pod = null;
        String podName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_POD_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(podName)) {
            LOG.error("Get a specific pod require specify a pod name");
            throw new IllegalArgumentException("Get a specific pod require specify a pod name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific pod require specify a namespace name");
            throw new IllegalArgumentException("Get a specific pod require specify a namespace name");
        }
        pod = getEndpoint().getKubernetesClient().pods().inNamespace(namespaceName).withName(podName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(pod);
    }

};