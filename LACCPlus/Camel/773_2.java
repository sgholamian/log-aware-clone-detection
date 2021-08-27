//,temp,PulsarConsumerInAsynchronousIT.java,91,97,temp,KubernetesCustomResourcesConsumerTest.java,120,127
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            String json = exchange.getIn().getBody(String.class);

            log.info("Got event with custom resource instance: " + json + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};