//,temp,KubernetesServicesConsumerTest.java,116,121,temp,KubernetesPodsConsumerTest.java,120,126
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            Pod pod = exchange.getIn().getBody(Pod.class);
            log.info("Got event with pod name: " + pod.getMetadata().getName() + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};