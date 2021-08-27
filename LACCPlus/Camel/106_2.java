//,temp,KubernetesConfigMapsConsumerTest.java,107,113,temp,KubernetesNodesConsumerTest.java,118,124
//,2
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            Node node = exchange.getIn().getBody(Node.class);
            log.info("Got event with node name: " + node.getMetadata().getName() + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};