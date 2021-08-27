//,temp,CordaConsumer.java,235,240,temp,CordaConsumer.java,200,205
//,3
public class xxx {
    private void processFlowProcess(String x) {
        LOG.debug("processFlowProcess {}", x);
        Exchange exchange = createExchange(true);
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};