//,temp,sample_1976.java,2,11,temp,sample_4244.java,2,10
//,3
public class xxx {
protected long determineRedeliveryDelay(Exchange exchange, RedeliveryPolicy redeliveryPolicy, long redeliveryDelay, int redeliveryCounter) {
Message message = exchange.getIn();
Long delay = message.getHeader(Exchange.REDELIVERY_DELAY, Long.class);
if (delay == null) {
delay = redeliveryPolicy.calculateRedeliveryDelay(redeliveryDelay, redeliveryCounter);


log.info("redelivery delay calculated as");
}
}

};