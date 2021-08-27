//,temp,KubernetesNodesProducer.java,107,118,temp,KubernetesPersistentVolumesProducer.java,99,110
//,2
public class xxx {
    protected void doGetNode(Exchange exchange, String operation) throws Exception {
        Node node = null;
        String pvName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NODE_NAME, String.class);
        if (ObjectHelper.isEmpty(pvName)) {
            LOG.error("Get a specific Node require specify a Node name");
            throw new IllegalArgumentException("Get a specific Node require specify a Node name");
        }
        node = getEndpoint().getKubernetesClient().nodes().withName(pvName).get();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(node);
    }

};