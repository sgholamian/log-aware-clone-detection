//,temp,sample_1791.java,2,12,temp,sample_2467.java,2,13
//,3
public class xxx {
protected boolean continueProcessing(Exchange exchange, AsyncProcessor processor) {
Object stop = exchange.getProperty(Exchange.ROUTE_STOP);
if (stop != null) {
boolean doStop = exchange.getContext().getTypeConverter().convertTo(Boolean.class, stop);
if (doStop) {


log.info("exchange is marked to stop routing");
}
}
}

};