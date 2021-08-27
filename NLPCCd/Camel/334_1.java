//,temp,sample_1358.java,2,11,temp,sample_4580.java,2,12
//,3
public class xxx {
protected void assertCorrectMapReceived() {
Exchange exchange = endpoint.getReceivedExchanges().get(0);
assertNotNull(ExchangeHelper.getBinding(exchange, JmsBinding.class));
JmsMessage in = (JmsMessage) exchange.getIn();
assertNotNull(in);
Map<?, ?> map = exchange.getIn().getBody(Map.class);


log.info("received map");
}

};