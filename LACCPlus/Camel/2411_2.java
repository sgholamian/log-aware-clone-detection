//,temp,KubernetesNamespacesProducer.java,93,108,temp,KubernetesHPAProducer.java,95,112
//,3
public class xxx {
    protected void doListHPAByLabel(Exchange exchange, String operation) {
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_LABELS, Map.class);
        if (ObjectHelper.isEmpty(labels)) {
            LOG.error("Get HPA by labels require specify a labels set");
            throw new IllegalArgumentException("Get HPA by labels require specify a labels set");
        }

        MixedOperation<HorizontalPodAutoscaler, HorizontalPodAutoscalerList, Resource<HorizontalPodAutoscaler>> hpas
                = getEndpoint()
                        .getKubernetesClient().autoscaling().v1().horizontalPodAutoscalers();
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            hpas.withLabel(entry.getKey(), entry.getValue());
        }
        HorizontalPodAutoscalerList hpaList = hpas.list();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(hpaList.getItems());
    }

};