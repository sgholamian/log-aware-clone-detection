//,temp,KubernetesReplicationControllersConsumerTest.java,120,125,temp,SplitterParallelIssueTest.java,61,67
//,3
public class xxx {
        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            log.info("Got event with body: " + in.getBody() + " and action "
                     + in.getHeader(KubernetesConstants.KUBERNETES_EVENT_ACTION));
        }

};