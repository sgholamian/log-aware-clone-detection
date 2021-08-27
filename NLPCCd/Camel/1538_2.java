//,temp,sample_6959.java,2,18,temp,sample_6963.java,2,17
//,3
public class xxx {
public void done(boolean doneSync) {
if (doneSync) {
return;
}
while (continueRouting(processors, exchange)) {
exchange.setProperty(Exchange.TRY_ROUTE_BLOCK, true);
ExchangeHelper.prepareOutToIn(exchange);
AsyncProcessor processor = AsyncProcessorConverterHelper.convert(processors.next());
doneSync = process(exchange, callback, processors, processor, lastHandled);
if (!doneSync) {


log.info("processing exchangeid is continued being processed asynchronously");
}
}
}

};