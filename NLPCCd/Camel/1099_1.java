//,temp,sample_1404.java,2,16,temp,sample_1403.java,2,16
//,3
public class xxx {
public void dummy_method(){
Producer producer = gatewayEndpoint.createProducer();
Email email = TestSupport.createEmailMessage("Dynamic Routing Example");
email.getHeader().setString(DeliverToCompID.FIELD, "TRADER@2");
Exchange exchange = producer.createExchange(ExchangePattern.InOnly);
exchange.getIn().setBody(email);
producer.process(exchange);
if (!receivedMessageLatch.await(5, TimeUnit.SECONDS)) {
throw new IllegalStateException("Message did not reach target");
}
context.stop();


log.info("dynamic routing example complete");
}

};