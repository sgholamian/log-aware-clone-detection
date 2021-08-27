//,temp,KubernetesNamespacesConsumerTest.java,119,124,temp,PredicateAsBeanTest.java,63,68
//,3
public class xxx {
        @Override
        public boolean matches(Exchange exchange) {
            LOG.info("matches(exchange) called with: " + exchange);
            body = exchange.getIn().getBody(String.class);
            return null != body && body.equals("Wobble");
        }

};