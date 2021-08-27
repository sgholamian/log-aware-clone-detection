//,temp,sample_2930.java,2,16,temp,sample_2929.java,2,16
//,3
public class xxx {
public void dummy_method(){
Endpoint gatewayEndpoint = context.getEndpoint(gatewayUri);
Producer producer = gatewayEndpoint.createProducer();
NewOrderSingle order = createNewOrderMessage();
Exchange exchange = producer.createExchange(ExchangePattern.InOnly);
exchange.getIn().setBody(order);
producer.process(exchange);
if (!executionReportLatch.await(5, TimeUnit.SECONDS)) {
throw new IllegalStateException("Did not receive execution reports");
}
context.stop();


log.info("order execution example complete");
}

};