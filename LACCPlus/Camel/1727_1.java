//,temp,KubernetesHPAProducer.java,114,131,temp,KubernetesConfigMapsProducer.java,107,123
//,3
public class xxx {
    protected void doGetHPA(Exchange exchange, String operation) throws Exception {
        HorizontalPodAutoscaler hpa = null;
        String podName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(podName)) {
            LOG.error("Get a specific hpa require specify an hpa name");
            throw new IllegalArgumentException("Get a specific hpa require specify an hpa name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Get a specific hpa require specify a namespace name");
            throw new IllegalArgumentException("Get a specific hpa require specify a namespace name");
        }
        hpa = getEndpoint().getKubernetesClient().autoscaling().v1().horizontalPodAutoscalers().inNamespace(namespaceName)
                .withName(podName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(hpa);
    }

};