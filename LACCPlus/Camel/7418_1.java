//,temp,KubernetesPodsProducer.java,98,113,temp,KubernetesJobProducer.java,94,109
//,3
public class xxx {
    protected void doListPodsByLabel(Exchange exchange, String operation) {
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PODS_LABELS, Map.class);
        if (ObjectHelper.isEmpty(labels)) {
            LOG.error("Get pods by labels require specify a labels set");
            throw new IllegalArgumentException("Get pods by labels require specify a labels set");
        }

        FilterWatchListMultiDeletable<Pod, PodList> pods = getEndpoint().getKubernetesClient().pods().inAnyNamespace();
        for (Map.Entry<String, String> entry : labels.entrySet()) {
            pods.withLabel(entry.getKey(), entry.getValue());
        }
        PodList podList = pods.list();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(podList.getItems());
    }

};