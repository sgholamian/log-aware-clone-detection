//,temp,KubernetesReplicationControllersProducer.java,202,225,temp,KubernetesHPAProducer.java,161,177
//,3
public class xxx {
    protected void doDeleteHPA(Exchange exchange, String operation) throws Exception {
        String hpaName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_HPA_NAME, String.class);
        String namespaceName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NAMESPACE_NAME, String.class);
        if (ObjectHelper.isEmpty(hpaName)) {
            LOG.error("Delete a specific hpa require specify a hpa name");
            throw new IllegalArgumentException("Delete a specific hpa require specify a hpa name");
        }
        if (ObjectHelper.isEmpty(namespaceName)) {
            LOG.error("Delete a specific hpa require specify a namespace name");
            throw new IllegalArgumentException("Delete a specific hpa require specify a namespace name");
        }
        boolean hpaDeleted = getEndpoint().getKubernetesClient().autoscaling().v1().horizontalPodAutoscalers()
                .inNamespace(namespaceName).withName(hpaName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(hpaDeleted);
    }

};