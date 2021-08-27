//,temp,sample_991.java,2,12,temp,sample_3717.java,2,14
//,3
public class xxx {
private void updateValue(final ObjectAddress address, final Value<?> value) {
try {
final Exchange exchange = getEndpoint().createExchange();
exchange.setIn(mapMessage(value));
getAsyncProcessor().process(exchange);
} catch (final Exception e) {


log.info("failed to process message");
}
}

};