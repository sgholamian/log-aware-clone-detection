//,temp,sample_3623.java,2,16,temp,sample_8036.java,2,17
//,3
public class xxx {
public void run() {
exchange.addOnCompletion(new AggregateOnCompletion(exchange.getExchangeId()));
try {
processor.process(exchange);
} catch (Throwable e) {
exchange.setException(e);
}
if (exchange.getException() != null) {
getExceptionHandler().handleException("Error processing aggregated exchange", exchange, exchange.getException());
} else {


log.info("processing aggregated exchange complete");
}
}

};