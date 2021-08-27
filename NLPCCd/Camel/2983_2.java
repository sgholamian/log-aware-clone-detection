//,temp,sample_1791.java,2,12,temp,sample_2467.java,2,13
//,3
public class xxx {
protected boolean continueRouting(Iterator<Processor> it, Exchange exchange) {
boolean answer = true;
Object stop = exchange.getProperty(Exchange.ROUTE_STOP);
if (stop != null) {
boolean doStop = exchange.getContext().getTypeConverter().convertTo(Boolean.class, stop);
if (doStop) {


log.info("exchangeid is marked to stop routing");
}
}
}

};