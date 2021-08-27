//,temp,sample_6982.java,2,16,temp,sample_5393.java,2,13
//,3
public class xxx {
public void testPutAndGetNotFound() {
Exchange exchange = new DefaultExchange(context);
exchange.getIn().setBody("Hello World");
repo.add(context, exchange.getExchangeId(), exchange);
Exchange out = repo.get(context, exchange.getExchangeId());
assertNotNull("Should find exchange", out);
Exchange exchange2 = new DefaultExchange(context);
exchange2.getIn().setBody("Bye World");


log.info("created");
}

};