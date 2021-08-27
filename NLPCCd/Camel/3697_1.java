//,temp,sample_5691.java,2,15,temp,sample_5690.java,2,14
//,3
public class xxx {
protected Processor createErrorHandler(RouteContext routeContext, Exchange exchange, Processor processor) {
Processor answer;
boolean tryBlock = exchange.getProperty(Exchange.TRY_ROUTE_BLOCK, false, boolean.class);
if (!tryBlock && routeContext != null) {
final PreparedErrorHandler key = new PreparedErrorHandler(routeContext, processor);
answer = errorHandlers.get(key);
if (answer != null) {
return answer;
}


log.info("creating error handler for");
}
}

};