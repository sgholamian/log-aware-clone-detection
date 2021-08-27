//,temp,KubernetesNodesProducer.java,141,151,temp,KubernetesJobProducer.java,155,170
//,3
public class xxx {
    protected void doDeleteNode(Exchange exchange, String operation) throws Exception {
        String nodeName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NODE_NAME, String.class);
        if (ObjectHelper.isEmpty(nodeName)) {
            LOG.error("Deleting a specific Node require specify a Node name");
            throw new IllegalArgumentException("Deleting a specific Node require specify a Node name");
        }
        boolean nodeDeleted = getEndpoint().getKubernetesClient().nodes().withName(nodeName).delete();

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(nodeDeleted);
    }

};