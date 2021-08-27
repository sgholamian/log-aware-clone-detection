//,temp,sample_6965.java,2,12,temp,sample_6628.java,2,12
//,3
public class xxx {
protected boolean continueProcessing(Exchange exchange) {
Object stop = exchange.getProperty(Exchange.ROUTE_STOP);
if (stop != null) {
boolean doStop = exchange.getContext().getTypeConverter().convertTo(Boolean.class, stop);
if (doStop) {


log.info("exchange is marked to stop routing");
}
}
}

};