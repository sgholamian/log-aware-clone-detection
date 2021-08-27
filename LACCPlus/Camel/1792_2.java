//,temp,KubernetesReplicationControllersProducer.java,153,181,temp,KubernetesHPAProducer.java,133,159
//,3
public class xxx {
    protected void doCreateHPA(Exchange exchange, String operation) throws Exception {
        HorizontalPodAutoscaler hpa = null;
        String hpaName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        HorizontalPodAutoscalerSpec hpaSpec
                = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_SPEC, HorizontalPodAutoscalerSpec.class);
        if (ObjectHelper.isEmpty(hpaName)) {
            LOG.error("Create a specific hpa require specify a hpa name");
            throw new IllegalArgumentException("Create a specific hpa require specify a hpa name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Create a specific hpa require specify a namespace name");
            throw new IllegalArgumentException("Create a specific hpa require specify a namespace name");
        }
        if (ObjectHelper.isEmpty(hpaSpec)) {
            LOG.error("Create a specific hpa require specify a hpa spec bean");
            throw new IllegalArgumentException("Create a specific hpa require specify a hpa spec bean");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_LABELS, Map.class);
        HorizontalPodAutoscaler hpaCreating = new HorizontalPodAutoscalerBuilder().withNewMetadata().withName(hpaName)
                .withLabels(labels).endMetadata().withSpec(hpaSpec).build();
        hpa = getEndpoint().getKubernetesClient().autoscaling().v1().horizontalPodAutoscalers().inNamespace(namespaceName)
                .create(hpaCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(hpa);
    }

};