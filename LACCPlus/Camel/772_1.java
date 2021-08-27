//,temp,KubernetesNamespacesConsumerTest.java,119,124,temp,PredicateAsBeanTest.java,63,68
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            log.info("Got event with body: " + in.getBody() + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};