//,temp,KubernetesSecretsProducer.java,134,150,temp,KubernetesNodesProducer.java,120,139
//,3
public class xxx {
    protected void doCreateNode(Exchange exchange, String operation) throws Exception {
        Node node = null;
        String nodeName = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NODE_NAME, String.class);
        NodeSpec nodeSpec = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_NODE_SPEC, NodeSpec.class);
        if (ObjectHelper.isEmpty(nodeName)) {
            LOG.error("Create a specific node require specify a node name");
            throw new IllegalArgumentException("Create a specific node require specify a node name");
        }
        if (ObjectHelper.isEmpty(nodeSpec)) {
            LOG.error("Create a specific node require specify a node spec bean");
            throw new IllegalArgumentException("Create a specific node require specify a node spec bean");
        }
        Map<String, String> labels = exchange.getIn().getHeader(KubernetesConstants.KUBERNETES_PODS_LABELS, Map.class);
        Node nodeCreating = new NodeBuilder().withNewMetadata().withName(nodeName).withLabels(labels).endMetadata()
                .withSpec(nodeSpec).build();
        node = getEndpoint().getKubernetesClient().nodes().create(nodeCreating);

        MessageHelper.copyHeaders(exchange.getIn(), exchange.getOut(), true);
        exchange.getOut().setBody(node);
    }

};