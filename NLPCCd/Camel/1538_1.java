//,temp,sample_6959.java,2,18,temp,sample_6963.java,2,17
//,3
public class xxx {
public void dummy_method(){
Iterator<Processor> processors = next().iterator();
Object lastHandled = exchange.getProperty(Exchange.EXCEPTION_HANDLED);
exchange.setProperty(Exchange.EXCEPTION_HANDLED, null);
while (continueRouting(processors, exchange)) {
exchange.setProperty(Exchange.TRY_ROUTE_BLOCK, true);
ExchangeHelper.prepareOutToIn(exchange);
Processor processor = processors.next();
AsyncProcessor async = AsyncProcessorConverterHelper.convert(processor);
boolean sync = process(exchange, callback, processors, async, lastHandled);
if (!sync) {


log.info("processing exchangeid is continued being processed asynchronously");
}
}
}

};