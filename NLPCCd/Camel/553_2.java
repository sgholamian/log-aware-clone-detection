//,temp,sample_991.java,2,12,temp,sample_3717.java,2,14
//,3
public class xxx {
private CompletionStage<Void> updateValue(final Request<?> value) {
try {
final Exchange exchange = getEndpoint().createExchange();
exchange.setIn(mapMessage(value));
final CompletableFuture<Void> result = new CompletableFuture<>();
getAsyncProcessor().process(exchange, doneSync -> result.complete(null));
return result;
} catch (final Exception e) {


log.info("failed to process message");
}
}

};