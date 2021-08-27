//,temp,KubernetesConfigMapsConsumerTest.java,107,113,temp,KubernetesNodesConsumerTest.java,118,124
//,2
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            ConfigMap cm = exchange.getIn().getBody(ConfigMap.class);
            log.info("Got event with configmap name: " + cm.getMetadata().getName() + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};